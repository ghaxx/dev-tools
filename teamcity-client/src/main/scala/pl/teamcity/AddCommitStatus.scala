package pl.teamcity

import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, JObject, JValue}
import pl.teamcity.client.TcClient
import pl.teamcity.model.{Build, LazyBuild}
import pl.teamcity.model.creation.CommitStatusFeature
import pl.teamcity.services.BuildService

object AddCommitStatus extends App {

  implicit val formats = DefaultFormats

  import Projects._

  def l(j: JValue) = println(Serialization.writePretty(j))

  val client = TcClient.default

  val jcsBuild = new LazyBuild(jcs, client)

  val buildService = new BuildService(client)

  val builds = List(
  )
//  println(buildService.getBuild("otc_AcmeApiBuild").getFeatures)
//  buildService.getAll.foreach {
//    buildJson =>
//      val buildId = (buildJson \ "id").extract[String]
//      if (builds.contains(buildId)) {
//        val build = buildService.getBuild(buildId)
//              build.getFeatures.foreach(println)
//              if (build.hasFeature("VcsLabeling") && !build.hasFeature("commit-status-publisher")) {
//                build.addFeature(CommitStatusFeature(buildId))
//                println(s"Adding github to $buildId")
//              }
//      }
//  }
//  buildService.getAll.foreach {
//    buildJson =>
//      val buildId = (buildJson \ "id").extract[String]
//      if (builds.contains(buildId)) {
//        val build = buildService.getBuild(buildId)
//        if (build.hasFeature("commit-status-publisher")) {
//          println(build.getFeatureByType("commit-status-publisher"))
//        }
//      }
//  }
  buildService.getAll.foreach {
    buildJson =>
      val buildId = (buildJson \ "id").extract[String]
      if (builds.contains(buildId)) {
        val build = buildService.getBuild(buildId)
        if (build.hasFeature("commit-status-publisher")) {
          val f = build.getFeatureByType("commit-status-publisher")
          f.properties.
        }
      }
  }


    val build = buildService.getBuild("build")
  val features =  build.getFeatures
//  println(build.getFeatureByType("commit-status-publisher"))
//  println(build.getFeature("BUILD_EXT_93"))

  println(client.buildTypes.features.getFeatureProperty("build", "BUILD_EXT_93", "github_authentication_type"))
  println(client.buildTypes.features.getFeatureProperty("build", "BUILD_EXT_93", "secure:github_access_token"))

  client.close()
}
