package pl.github.domain.services

import pl.github.domain.model.account.user.{IdentifiedUser, MyUser}
import pl.github.domain.model.account.{Account, Organization, User}
import pl.github.domain.services.http.GitHubHttpClient

class GitHubService extends AutoCloseable {
  private val client = new GitHubHttpClient

  def getMyUser: User = {
    new MyUser(client)
  }

  def getUser(login: Account.Login): User = {
    new IdentifiedUser(login)(client)
  }

  def getOrganization(login: Account.Login): Organization = {
    new Organization(login)(client)
  }

  def close(): Unit = client.close()
}
