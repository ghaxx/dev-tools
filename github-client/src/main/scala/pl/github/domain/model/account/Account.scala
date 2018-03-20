package pl.github.domain.model.account

import pl.github.domain.model.account.user.MyUser
import pl.github.domain.model.repository.Repository
import pl.github.domain.model.repository.Repository.Id
import pl.github.domain.services.http.GitHubHttpClient

trait Account {
  def login: Account.Login
  def repository(name: Repository.Name): Repository
  def repositoriesList: List[Repository]
}

object Account {
  case class Login(value: String) extends AnyVal {
    override def toString: String = value
  }

  def apply(login: String)(implicit ghHttp: GitHubHttpClient): Account = IdentifiedUser(Login(login))(ghHttp)


  def getMyUser(implicit client: GitHubHttpClient): User = {
    new MyUser(client)
  }

  def getUser(login: Account.Login)(implicit client: GitHubHttpClient): User = {
    new IdentifiedUser(login)(client)
  }

  def getOrganization(login: Account.Login)(implicit client: GitHubHttpClient): Organization = {
    Organization(login)(client)
  }

}








