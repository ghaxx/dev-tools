package pl.teamcity

import org.json4s.{DefaultFormats, JObject, JValue}
import org.json4s.native.Serialization
import pl.teamcity.client.TcClient
import pl.teamcity.model.{Build, LazyBuild}
import pl.teamcity.model.creation.CommitStatusFeature

object BuildFeatures extends App {

  implicit val formats = DefaultFormats

  import Projects._

  def l(j: JValue) = println(Serialization.writePretty(j))

  val client = TcClient.default

  val jcsBuild = new LazyBuild(jcs, client)

  //  jcsBuild.addFeature(CommitStatusFeature())
  //  println(Serialization.writePretty(jcsBuild.getFeatures))


  val jawpApiBuild = new LazyBuild(jawpApi, client)
  //  jawpApiBuild.addFeature(CommitStatusFeature("common"))
  //  l(jawpApiBuild.getFeatures)
  (client.buildTypes.getAllBuilds \ "buildType")
    .extract[List[JObject]]
    .foreach {
      build =>
        val name = (build \ "name").extract[String]
        val id = (build \ "id").extract[String]
        if (id.startsWith("otc_")) println(s"$name -> $id")
    }

  //  l(client.vcsRoots.getAll)
  client.close()
}
