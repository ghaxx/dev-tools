package pl.github.cases

import org.json4s.DefaultFormats
import pl.github.data.Repos
import pl.github.domain.model.account.Account
import pl.github.domain.model.repository.Branch
import pl.github.domain.services.GitHubService
import pl.github.domain.services.http.GitHubHttpClient

object DefaultBranches extends App {

  val service = new GitHubService
  val myUser = service.getMyUser
  println(s"I'm ${myUser.name.value}")
  myUser.repositoriesList.foreach {
    repo =>
      println(s"${repo.name.value}: ${repo.defaultBranch.name.value}")
//      repo.setDefaultBranch(Branch.Name("master"))
  }

  service.close()

}
