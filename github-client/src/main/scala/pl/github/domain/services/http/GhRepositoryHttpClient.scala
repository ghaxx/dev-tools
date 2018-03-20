package pl.github.domain.services.http

import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.account.Account
import pl.github.domain.model.repository.{Branch, PreloadedPullRequest, PullRequest, Repository}
import pl.github.domain.model.{GitHubKey, GitHubKeyCreationData, RepoUpdateData}

class GhRepositoryHttpClient(ghHttp: GhHttpConnector) {
  implicit val formats = DefaultFormats

  def patchRepo(owner: String, data: RepoUpdateData): Unit = {
    val url = s"/repos/$owner/${data.name}"
    ghHttp.patchRequest(url, Serialization.write(data))
  }

  def getRepo(owner: String, repo: String): JValue = {
    val url = s"/repos/$owner/$repo"
    Serialization.read[JValue](ghHttp.getRequest(url).body)
  }

  def setCommitStatus(
    owner: String,
    repo: String,
    hash: String,
    state: String,
    targetUrl: String,
    description: String,
    context: String
  ) = {
    val url = s"/repos/$owner/$repo/statuses/$hash"
    val data =
      s"""{
         |  "state": "$state",
         |  "target_url": "$targetUrl",
         |  "description": "$description",
         |  "context": "$context"
         |}
        """.stripMargin
    ghHttp.postRequest(url, data)
  }

  def getCommitStatus(
    owner: String,
    repo: String,
    hash: String
  ) = {
    val url = s"/repos/$owner/$repo/statuses/$hash"
    ghHttp.getRequest(url)
  }

  def listKeys(owner: String, repo: String): List[GitHubKey] = {
    val url = s"/repos/$owner/$repo/keys"
    Serialization.read[List[GitHubKey]](ghHttp.getRequest(url).body)
  }

  def deleteKey(owner: String, repo: String, keyId: Int): Unit = {
    val url = s"/repos/$owner/$repo/keys/$keyId"
    ghHttp.deleteRequest(url)
  }

  def createKey(owner: String, repo: String, data: GitHubKeyCreationData): Unit = {
    val url = s"/repos/$owner/$repo/keys"
    ghHttp.postRequest(url, Serialization.write(data))
  }

  def listPulls(owner: Account.Login, repo: Repository.Name):  List[JValue] = {
    val url = s"/repos/${owner.value}/${repo.value}/pulls"
    val response = ghHttp.getRequest(url)
    Serialization
      .read[List[JValue]](response.body)
  }

  def listCommits(owner: Account.Login, repo: Repository.Name, author: Account.Login, since: String):  List[JValue] = {
    val url = s"/repos/${owner.value}/${repo.value}/commits"
    val response = ghHttp.getRequest(url, Map("author" -> author.value, "since" -> since))
    Serialization
      .read[List[JValue]](response.body)
  }


  def getBranch(owner: Account.Login, repo: Repository.Name, branch: Branch.Name) = {
    val url = s"/repos/${owner.value}/${repo.value}/branches/${branch.value}"
    Serialization.read[JValue](ghHttp.getRequest(url).body)
  }


  def protectBranch(owner: Account.Login, repo: Repository.Name, branch: Branch.Name, protector: Account.Login*) = {
    val data =
      s"""
         |{
         |  "required_status_checks": {
         |    "include_admins": true,
         |    "strict": true,
         |    "contexts": [
         |      "continuous-integration/teamcity"
         |    ]
         |  },
         |  "required_pull_request_reviews": {
         |    "include_admins": false
         |  },
         |  "restrictions": {
         |    "users": [
         |      ${protector.map('"' + _.value + '"').mkString(",")}
         |    ],
         |    "teams": [
         |    ]
         |  }
         |}
        """.stripMargin

    val url = s"/repos/${owner.value}/${repo.value}/branches/${branch.value}/protection"
    ghHttp.putRequest(url, data, "application/vnd.github.loki-preview+json")
  }

  def unprotectBranch(owner: String, repo: String, branch: String) = {
    val url = s"/repos/$owner/$repo/branches/$branch/protection"
    ghHttp.deleteRequest(url, null, "application/vnd.github.loki-preview+json")
  }
}
