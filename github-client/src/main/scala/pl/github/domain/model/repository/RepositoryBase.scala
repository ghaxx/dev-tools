package pl.github.domain.model.repository

import org.json4s.DefaultFormats
import pl.github.domain.model.GitHubKey.Id
import pl.github.domain.model.account.Account
import pl.github.domain.model.{GitHubKeyCreationData, RepoUpdateData}
import pl.github.domain.model.repository.Branch.Name
import pl.github.domain.services.http.GitHubHttpClient

abstract class RepositoryBase extends Repository {

  private implicit val formats = DefaultFormats

  def ghHttp: GitHubHttpClient

  def keysList = ghHttp.repo.listKeys(owner.login.value, name.value)
  def createKey(key: GitHubKeyCreationData) = ghHttp.repo.createKey(owner.login.value, name.value, key)
  def deleteKey(id: Id) = ghHttp.repo.deleteKey(owner.login.value, name.value, id.value)
  def commit(hash: Commit.Hash) = Commit(this, hash)(ghHttp)

  override def pulls = {
    ghHttp.repo.listPulls(owner.login, name)
  }

  def branch(name: Branch.Name) = new Branch(this, name)(ghHttp)
  def defaultBranch = {
    new Branch(this, Branch.Name((json \ "default_branch").extract[String]))(ghHttp)
  }

  override def setDefaultBranch(name: Name) = {
    val data = RepoUpdateData(name.value, default_branch = Some(name.value))
    ghHttp.repo.patchRepo(owner.login.value, data)
  }

  def protectBranch(branchName: Branch.Name, protector: Account*) = {
    ghHttp.repo.protectBranch(owner.login, name, branchName, protector.map(_.login): _*)
  }
}
