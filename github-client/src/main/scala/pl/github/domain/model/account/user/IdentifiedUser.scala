package pl.github.domain.model.account.user

import org.json4s.DefaultFormats
import pl.github.domain.model.account.{Account, User}
import pl.github.domain.model.repository.Repository.Id
import pl.github.domain.model.repository.{LazyRepository, Repository, PreloadedRepository}
import pl.github.domain.services.http.GitHubHttpClient

class IdentifiedUser(val login: Account.Login)(client: GitHubHttpClient) extends User {

  private implicit val formats = DefaultFormats
  private lazy val details = client.user.getDetails(login.value)

  lazy val name = User.Name((details \ "name").extract[String])

  def repository(name: Repository.Name): Repository = {
    LazyRepository(this, name)(client)
  }

  def repositoriesList: List[Repository] = {
    client.user.getRepos(login.value).map(PreloadedRepository(this, _)(client))
  }

}
