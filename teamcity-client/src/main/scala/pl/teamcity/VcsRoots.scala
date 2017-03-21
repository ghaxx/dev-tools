package pl.teamcity

import org.json4s.DefaultFormats
import pl.teamcity.client.TcClient
import pl.teamcity.services.VcsRootService

object VcsRoots extends App {

  implicit val formats = DefaultFormats

  val client = TcClient.default

  val vcsService = new VcsRootService(client)
  vcsService.getAll.foreach {
    root =>
      println((root \ "id").extract[String])
      System.exit(0)
  }

}
