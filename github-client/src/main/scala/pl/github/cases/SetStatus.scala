package pl.github.cases

import pl.github.domain.model.repository.{Commit, Repository}
import pl.github.domain.services.GitHubService

object SetStatus extends App {

  val service = new GitHubService
  val myUser = service.getMyUser
  println(s"I'm ${myUser.name}")
  myUser
    .repository(Repository.Name("dev-tools"))
    .commit(Commit.Hash("3f4eff7febf0b836e16bd12b4d6b19ee885ca26a"))
    .setStatus(Commit.Status.Failure())

}
