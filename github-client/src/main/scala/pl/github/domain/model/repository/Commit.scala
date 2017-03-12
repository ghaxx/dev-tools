package pl.github.domain.model.repository

import pl.github.domain.services.http.GitHubHttpClient

case class Commit(
  repo: Repository,
  hash: Commit.Hash
)(ghHttp: GitHubHttpClient) {
  def setStatus(status: Commit.Status) = {
    ghHttp.repo.setCommitStatus(
      repo.owner.login.value,
      repo.name.value,
      hash.value,
      status.`type`,
      status.url,
      status.description,
      status.context
    )
  }
}

object Commit {
  case class Hash(value: String) extends AnyVal

  sealed trait Status {
    def `type`: String
    def url: String
    def description: String
    def context: String
  }
  object Status {
    case class Failure(
      url: String = "",
      description: String = "The build failed.",
      context: String = ""
    ) extends Status {
      val `type` = "failure"
    }
    case class Success(
      url: String = "",
      description: String = "The build succeeded.",
      context: String = ""
    ) extends Status {
      val `type` = "success"
    }
    case class Pending(
      url: String = "",
      description: String = "The build hasn't finished yet.",
      context: String = ""
    ) extends Status {
      val `type` = "pending"
    }
  }
}