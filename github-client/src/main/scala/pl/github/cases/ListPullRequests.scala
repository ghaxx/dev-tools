package pl.github.cases

import pl.github.domain.model.account.Account
import pl.github.domain.services.GitHubService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object ListPullRequests extends App {
import scala.concurrent.ExecutionContext.Implicits.global
//  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(16))

  val service = new GitHubService
  val user = service.getMyUser
  val pullsPerRepoFut = user.repositoriesList.map {
    repo =>
      Future {
        val pulls = repo.pulls
        if (pulls.nonEmpty) {
          pulls.foldLeft(repo.name.value -> List.empty[String]) {
            case ((r, l), pull) =>
              r -> (pull.htmlUrl :: l)
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
                case (pullUrl, i) => println(f" $i%3d: ${pullUrl}")
              }
            }
          }
      }
  }

  Await.result(Future.sequence(g), Duration.Inf)

  service.close()
}
