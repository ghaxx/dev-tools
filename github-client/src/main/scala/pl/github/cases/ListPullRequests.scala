package pl.github.cases

import pl.github.domain.model.account.Account
import pl.github.domain.services.GitHubService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object ListPullRequests extends App {
import scala.concurrent.ExecutionContext.Implicits.global
//  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(16))

  implicit val client = new GitHubHttpClient
  val user = Account.getMyUser
  val pullsPerRepoFut = user.repositoriesList.map {
    repo =>
      Future {
        val pulls = repo.pulls
        if (pulls.nonEmpty) {
          pulls.foldLeft(repo.name.value -> List.empty[String]) {
            case ((r, l), pull) =>
              val txt = s"${pull.user.name.value} - ${pull.htmlUrl}"
              r -> (txt :: l)
          }
        } else {
          repo.name.value -> List.empty[String]
        }
      }
  }

  val g = pullsPerRepoFut.map {
    pullsFut =>
      pullsFut.map {
        case (repo, pulls) =>
          pullsPerRepoFut.synchronized {
            if (pulls.nonEmpty) {
              println(repo)
              pulls.zipWithIndex.foreach {
                case (pullTxt, i) => println(f" $i%3d. $pullTxt")
              }
            }
          }
      }
  }

  Await.result(Future.sequence(g), Duration.Inf)

  service.close()
}
