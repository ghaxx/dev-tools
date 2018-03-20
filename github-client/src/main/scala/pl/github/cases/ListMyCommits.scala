package pl.github.cases

import java.util.Date

import pl.github.domain.model.account.Account
import pl.github.domain.model.repository.{Commit, Repository}
import pl.github.domain.services.http.GitHubHttpClient

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Try

object ListMyCommits extends App {
  implicit val client = new GitHubHttpClient

  try {

    import scala.concurrent.ExecutionContext.Implicits.global

    val user = Account.getOrganization(Account.Login("org"))
    val myUser = Account.getMyUser

    val commitsPerRepo: Future[List[(Repository, List[Commit])]] = Future.sequence(
              user.repositoriesList.map {
//      List(Repository(user, "jawp")).map {
        repo =>
          println(repo.name.value)
          Future {
            val commits = Try {
              repo.commits(myUser, "2018-03-01T00:00:00Z")
            }.getOrElse(List.empty)
            if (commits.nonEmpty) {
              commits.foldLeft(repo -> List.empty[Commit]) {
                case ((r, l), commit) =>
                  r -> (commit :: l)
              }
            } else {
              repo -> List.empty[Commit]
            }
          }
      })

    val g = commitsPerRepo.map {
      commitsPerRepo =>
        val reposAndCommits = commitsPerRepo.flatMap {
          case (repo, commits) => commits.map {
            commit => repo -> commit
          }
        }
        val datesAndCommits = reposAndCommits.map {
          case (repo, commit) => (commit.date.substring(0, 10)) -> (repo, commit)
        }
        datesAndCommits.groupBy(_._1).toList.sortBy(_._1).map {
          case (day, commits) =>
            println("--------------------------------")
            println(day)
            commits.foreach {
              case (date, (repo, commit)) =>
                if (!commit.message.startsWith("Merge pull request"))
                  println(s"  ${repo.name.value}: ${commit.author} - ${commit.message}")
            }
            1
        }
    }

    println(new Date())
    Await.result(g, Duration.Inf)
  } finally {
    service.close()
  }
}
