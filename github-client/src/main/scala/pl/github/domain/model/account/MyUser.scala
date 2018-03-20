package pl.github.domain.model.account

import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import pl.github.domain.model.repository.{LazyRepository, PreloadedRepository, Repository}
import pl.github.domain.services.http.GitHubHttpClient

class MyUser(client: GitHubHttpClient) extends User {

  private implicit val formats = DefaultFormats
  private lazy val details = client.myUser.getMyDetails

  println(Serialization.writePretty(details))

  lazy val name = User.Name((details \ "name").extract[String])
  lazy val login = Account.Login((details \ "login").extract[String])


  def repository(name: Repository.Name): Repository = {
    LazyRepository(this, name)(client)
  }

  def repositoriesList: List[Repository] = {
    client.myUser.getRepos.map(PreloadedRepository(_)(client))
  }

}
