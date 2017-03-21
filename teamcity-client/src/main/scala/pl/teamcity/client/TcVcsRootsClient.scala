package pl.teamcity.client

import org.json4s.JsonAST.JObject
import pl.teamcity.client.infrastructure.TcHttpService
import pl.teamcity.model.creation.BuildStep

class TcVcsRootsClient(httpClient: TcHttpService) {

  val properties = new TcFeaturesClient(httpClient)

  def getAll: JObject = {
    httpClient.get(s"/app/rest/vcs-roots")
  }

  def get(locator: String): JObject = {
    httpClient.get(s"/app/rest/vcs-root/$locator")
  }

  def close() = httpClient.close()
}
