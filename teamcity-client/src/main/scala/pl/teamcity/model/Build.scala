package pl.teamcity.model

import org.json4s.DefaultFormats
import org.json4s.JsonAST.JObject
import pl.teamcity.client.TcClient
import pl.teamcity.model.creation.CommitStatusFeature

trait Build {
  def getFeatures: List[Feature]
  def hasFeature(`type`: String): Boolean
  def getFeature(id: String): Feature
  def addFeature(data: CommitStatusFeature): JObject
}

class LazyBuild(project: String, client: TcClient) extends Build {
  private implicit val formats = DefaultFormats

  lazy val getFeatures: List[Feature] = {
    (client.buildTypes.features.getBuildFeatures(project) \ "feature").extract[List[Feature]]
  }

  def hasFeature(`type`: String): Boolean = {
    getFeatures.exists(_.`type` == `type`)
  }

  def getFeature(id: String): Feature = {
    getFeatures.find(_.id == id).get
    val x = client.buildTypes.features.getBuildFeature(project, id)
    println(x)
    (x).extract[Feature]
  }

  def getFeatureByType(`type`: String): Feature = {
    getFeatures.find(_.`type` == `type`).get
  }

  def addFeature(data: CommitStatusFeature): JObject = {
    client.buildTypes.features.addBuildFeature(project, data)
  }
}
