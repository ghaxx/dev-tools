package pl.github.domain.model.repository

import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.account.Account
import pl.github.domain.services.http.GitHubHttpClient

case class Branch(
  repo: Repository,
  name: Branch.Name
)(ghHttp: GitHubHttpClient) {
  private implicit val formats = DefaultFormats
  private lazy val json = ghHttp.repo.getBranch(repo.owner.login, repo.name, name)

  def protect(protector: Account*) =
    ghHttp.repo.protectBranch(repo.owner.login, repo.name, name, protector.map(_.login): _*)
  def head = RepositoryStatus(repo, RepositoryStatus.Hash((json \ "commit" \ "sha").extract[String]))(ghHttp)
}

object Branch {
  case class Name(value: String) extends AnyVal
}
