package pl.github.cases

import pl.github.domain.model.GitHubKeyCreationData
import pl.github.domain.model.account.Account
import pl.github.domain.model.repository.Repository
import pl.github.domain.services.GitHubService

object UploadKeys extends App {
  implicit val client = new GitHubHttpClient
  try {
    val myUser = service.getOrganization(Account.Login("org"))
    println(s"I'm ${myUser.login}")
//    myUser.repositoriesList.foreach {
//      repo =>
//        print(s"${repo.name}")
//        repo.keysList.foreach {
//          key =>
//            print(s"${key.id}")
//                  repo.deleteKey(key.id)
//          //        repo.createKey(GitHubKeyCreationData(
//          //          key.title, key.key, false
//          //        ))
//
//        }
//        println()
//    }
  } finally {
    service.close()
  }
}
