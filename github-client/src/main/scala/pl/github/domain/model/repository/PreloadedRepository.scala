package pl.github.domain.model.repository

import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.GitHubKey.Id
import pl.github.domain.model.GitHubKeyCreationData
import pl.github.domain.model.account.Account
import pl.github.domain.model.account.user.IdentifiedUser
import pl.github.domain.model.repository.Branch.Name
import pl.github.domain.services.http.GitHubHttpClient

case class PreloadedRepository(
  json: JValue
)(val ghHttp: GitHubHttpClient) extends RepositoryBase {

  private implicit val formats = DefaultFormats

  lazy val name = Repository.Name((json \ "name").extract[String])

  val owner = {
    val login = Account.Login((json \ "owner" \ "login").extract[String])
    new IdentifiedUser(login)(ghHttp)
  }
}
