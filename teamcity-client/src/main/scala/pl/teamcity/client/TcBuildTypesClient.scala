package pl.teamcity.client

import org.json4s.JsonAST.JObject
import pl.teamcity.client.infrastructure.TcHttpService
import pl.teamcity.model.creation.BuildStep

class TcBuildTypesClient(httpClient: TcHttpService) {

  val features = new TcFeaturesClient(httpClient)

  def getAllBuilds = {
    httpClient.get(s"/app/rest/buildTypes")
  }

  def getBuildConfigStep(build: String, stepId: String): JObject = {
    httpClient.get(s"/app/rest/buildTypes/$build/steps/$stepId")
  }

  def postBuildConfigStep(build: String, data: BuildStep): JObject = {
    httpClient.post(s"/app/rest/buildTypes/$build/steps", data.json)
  }

  def close() = httpClient.close()
}