package pl.github.domain.model.repository

import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.account.{Account, User}
import pl.github.domain.model.account.user.IdentifiedUser
import pl.github.domain.services.http.GitHubHttpClient

trait PullRequest {
  def htmlUrl: String
  def user: User
}

class PreloadedPullRequest(json: JValue)(val ghHttp: GitHubHttpClient) extends PullRequest {
  private implicit val formats = DefaultFormats

  override def htmlUrl = (json \ "html_url").extract[String]
  lazy val user = {
    new IdentifiedUser(Account.Login((json \ "user" \ "login").extract[String]))(ghHttp)
  }
}