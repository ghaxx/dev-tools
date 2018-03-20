package pl.github.domain.model.repository

import pl.github.domain.services.http.GitHubHttpClient

case class RepositoryStatus(
  repo: Repository,
  hash: RepositoryStatus.Hash
)(ghHttp: GitHubHttpClient) {
  def setStatus(status: RepositoryStatus.Status) = {
    ghHttp.repo.setCommitStatus(
      repo.owner.login.value,
      repo.name.value,
      hash.value,
      status.`type`,
      status.targetUrl,
      status.description,
      status.context
    )
  }
  def getStatus = {
    ghHttp.repo.getCommitStatus(
      repo.owner.login.value,
      repo.name.value,
      hash.value
    )
  }
}

object RepositoryStatus {
  case class Hash(value: String) extends AnyVal

  sealed trait Status {
    def `type`: String
    def targetUrl: String
    def description: String
    def context: String
  }
  object Status {
    case class Failure(
      targetUrl: String = "https://localhost.com/local",
      description: String = "The build failed.",
      context: String = "manual"
    ) extends Status {
      val `type` = "failure"
    }
    case class Success(
      targetUrl: String = "https://localhost.com/local",
      description: String = "The build succeeded.",
      context: String = "manual"
    ) extends Status {
      val `type` = "success"
    }
    case class Pending(
      targetUrl: String = "",
      description: String = "The build hasn't finished yet.",
      context: String = ""
    ) extends Status {
      val `type` = "pending"
    }
  }
}
