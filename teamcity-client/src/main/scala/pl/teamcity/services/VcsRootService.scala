package pl.teamcity.services

import org.json4s.{DefaultFormats, JValue}
import pl.teamcity.client.TcClient
import pl.teamcity.model.{Build, VcsRoot}

class VcsRootService(client: TcClient) {

  implicit val formats = DefaultFormats

  def getAll: List[JValue] = {
    println(client.vcsRoots.getAll)
    (client.vcsRoots.getAll \ "vcsRoot").extract[List[JValue]]
  }
  def getVcsRoot(id: String) = new VcsRoot(id, client)
}