package pl.github.domain.model.account

import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import pl.github.domain.model.repository.Repository.Id
import pl.github.domain.model.repository.{LazyRepository, PreloadedRepository, Repository}
import pl.github.domain.services.http.GitHubHttpClient

class Organization(val login: Account.Login)(client: GitHubHttpClient) extends Account {

  private implicit val formats = DefaultFormats
  private lazy val details = client.org.getDetails(login.value)

  def repository(name: Repository.Name): Repository = {
    LazyRepository(this, name)(client)
  }

  def repositoriesList: List[Repository] = {
    client.org.getRepos(login.value).map(PreloadedRepository(this, _)(client))
  }

}
