package pl.github.cases

import pl.github.domain.model.account.Account
import pl.github.domain.model.repository.{RepositoryStatus, Repository}
import pl.github.domain.services.GitHubService

object SetStatus extends App {
  implicit val client = new GitHubHttpClient
  try {
    val myUser = service.getOrganization(Account.Login("org"))
    //  val myUser = Account.getMyUser
    println(s"I'm ${myUser}")
    val status = myUser
      .repository(Repository.Name("jls"))
      .status(RepositoryStatus.Hash("68cd483d25f5e276c4779a0a3e03fb4c2539d256"))
      .setStatus(RepositoryStatus.Status.Success())
    //          .setStatus(Commit.Status.Failure())
    //      .getStatus

    println(status)
  } finally {
    service.close()
  }
}
