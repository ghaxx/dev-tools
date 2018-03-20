package pl.github.cases

import pl.github.domain.model.repository.Branch
import pl.github.domain.services.GitHubService

object ProtectBranches extends App {

  implicit val client = new GitHubHttpClient
  val myUser = Account.getMyUser
//  println(s"I'm ${myUser.name.value}")
  myUser.repositoriesList.foreach {
    repo =>
      val develop = repo.branch(Branch.Name("develop")).protect(myUser)
  }

  service.close()

}
