package pl.github.domain.model.account

import pl.github.domain.model.repository.Repository
import pl.github.domain.model.repository.Repository.Id

trait Account {
  def login: Account.Login
  def repository(name: Repository.Name): Repository
  def repositoriesList: List[Repository]
}

object Account {
  case class Login(value: String) extends AnyVal {
    override def toString = value
  }
}








