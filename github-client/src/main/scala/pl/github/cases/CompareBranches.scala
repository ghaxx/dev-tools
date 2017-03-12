package pl.github.cases

import pl.github.domain.model.repository.Branch
import pl.github.domain.services.GitHubService

object CompareBranches extends App {

  val service = new GitHubService
  try {
    val myUser = service.getMyUser
    println(s"I'm ${myUser.name.value}")
    myUser.repositoriesList.foreach {
      repo =>
        try {
          val develop = repo.branch(Branch.Name("develop")).head.hash.value
          val master = repo.branch(Branch.Name("master")).head.hash.value
          println(s"${repo.name.value}: ${develop} == ${master} === ${develop == master}")
        } catch {
          case _: Throwable =>
        }
    }
  } finally {
    service.close()
  }

}
