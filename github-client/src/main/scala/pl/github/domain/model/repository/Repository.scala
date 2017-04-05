package pl.github.domain.model.repository

import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.{GitHubKey, GitHubKeyCreationData}
import pl.github.domain.model.account.Account

trait Repository {
  def json: JValue
  def name: Repository.Name
  def commit(hash: Commit.Hash): Commit
  def owner: Account
  def branch(name: Branch.Name): Branch
  def defaultBranch: Branch

  def pulls: List[PullRequest]

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
}

