package pl.github.domain.model.account

import org.json4s.DefaultFormats
import pl.github.domain.model.repository.{LazyRepository, PreloadedRepository, Repository}
import pl.github.domain.services.http.GitHubHttpClient

case class IdentifiedUser(login: Account.Login)(implicit val client: GitHubHttpClient) extends User {

  private implicit val formats = DefaultFormats
  private lazy val details = client.user.getDetails(login.value)

  lazy val name = User.Name((details \ "name").extract[String])

  def repository(name: Repository.Name): Repository = {
    LazyRepository(this, name)(client)
  }

  def repositoriesList: List[Repository] = {
    client.user.getRepos(login.value).map(PreloadedRepository(_)(client))
  }

}
