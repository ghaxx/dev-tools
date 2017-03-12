package pl.teamcity.services

import org.json4s.{DefaultFormats, JValue}
import pl.teamcity.client.TcClient
import pl.teamcity.model.{Build, LazyBuild}

class BuildService(client: TcClient) {
  private implicit val formats = DefaultFormats

  def getAll = {
    (client.buildTypes.getAllBuilds \ "buildType").extract[List[JValue]]
  }
  def getBuild(id: String) = new LazyBuild(id, client)
}
