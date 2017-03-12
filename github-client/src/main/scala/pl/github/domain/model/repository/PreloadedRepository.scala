package pl.github.domain.model.repository

import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.GitHubKey.Id
import pl.github.domain.model.GitHubKeyCreationData
import pl.github.domain.model.account.Account
import pl.github.domain.model.repository.Branch.Name
import pl.github.domain.services.http.GitHubHttpClient

case class PreloadedRepository(
  owner: Account,
  json: JValue
)(val ghHttp: GitHubHttpClient) extends RepositoryBase {

  private implicit val formats = DefaultFormats

  lazy val name = Repository.Name((json \ "name").extract[String])

}
