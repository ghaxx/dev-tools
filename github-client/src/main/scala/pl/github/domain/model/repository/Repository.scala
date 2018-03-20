package pl.github.domain.model.repository

import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.{GitHubKey, GitHubKeyCreationData}
import pl.github.domain.model.account.Account
import pl.github.domain.services.http.GitHubHttpClient

import scala.reflect.ClassTag

trait Repository {
  def json: JValue
  def name: Repository.Name
  def status(hash: RepositoryStatus.Hash): RepositoryStatus
  def owner: Account
  def branch(name: Branch.Name): Branch
  def defaultBranch: Branch

  def pulls: List[PullRequest]
  def commits(author: Account, since: String): List[Commit]

  // Keys
  def keysList: List[GitHubKey]
  def createKey(key: GitHubKeyCreationData)
  def deleteKey(id: GitHubKey.Id)

  def setDefaultBranch(name: Branch.Name)
  def protectBranch(name: Branch.Name, protector: Account*)

}

object Repository {
  case class Id(value: String) extends AnyVal
  case class Name(value: String) extends AnyVal

  def apply(
      owner: Account,
      name: Repository.Name
  )(implicit ghHttp: GitHubHttpClient): Repository = LazyRepository(owner, name)(ghHttp)

  def apply[T <: String](
      owner: Account,
      name: T
  )(implicit ghHttp: GitHubHttpClient, classTag: ClassTag[T]): Repository = LazyRepository(owner, Repository.Name(name))(ghHttp)
}

