package pl.teamcity.client

import org.json4s.DefaultFormats
import org.json4s.JsonAST.JObject
import pl.teamcity.client.infrastructure.TcHttpService
import pl.teamcity.model.creation.CommitStatusFeature

class TcFeaturesClient(httpClient: TcHttpService) {
  private implicit val formats = DefaultFormats

  def getBuildFeatures(build: String): JObject = {
    httpClient.get(s"/app/rest/buildTypes/$build/features")
  }
  def addBuildFeature(build: String, data: CommitStatusFeature): JObject = {
    httpClient.post(s"/app/rest/buildTypes/$build/features", data.json)
  }
  def getBuildFeature(build: String, featureId: String): JObject = {
    httpClient.get(s"/app/rest/buildTypes/$build/features/$featureId")
  }
  def getFeatureProperty(build: String, featureId: String, propertyName: String): JObject = {
    httpClient.get(s"/app/rest/buildTypes/$build/features/$featureId/properties/$propertyName")
  }
}
