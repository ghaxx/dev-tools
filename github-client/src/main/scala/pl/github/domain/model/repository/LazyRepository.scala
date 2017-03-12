package pl.github.domain.model.repository

import org.json4s.JValue
import pl.github.domain.model.GitHubKey.Id
import pl.github.domain.model.GitHubKeyCreationData
import pl.github.domain.model.account.Account
import pl.github.domain.services.http.GitHubHttpClient

case class LazyRepository(
  owner: Account,
  name: Repository.Name
)(val ghHttp: GitHubHttpClient) extends RepositoryBase {
  lazy val json = ghHttp.repo.getRepo(owner.login.value, name.value)
}
