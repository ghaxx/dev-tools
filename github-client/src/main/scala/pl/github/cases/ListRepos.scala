package pl.github.cases

import pl.github.domain.model.account.Account
import pl.github.domain.services.GitHubService

object ListRepos extends App {
  implicit val client = new GitHubHttpClient
  val myUser = Account.getMyUser
  println(s"I'm ${myUser.name.value} (${myUser.login.value})")
  myUser.repositoriesList.foreach(repo => println(repo.name))

//  val typesafehub = service.getUser(Account.Login("typesafehub"))
//  println(s"I'm ${typesafehub.name.value}")
//  typesafehub.repositoriesList.foreach(repo => println(repo.name))

  service.close()
}
