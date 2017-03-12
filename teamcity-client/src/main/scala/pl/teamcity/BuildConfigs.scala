package pl.teamcity

import com.typesafe.config.ConfigFactory
import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, JObject}
import pl.teamcity.client.TcClient

import scala.util.Try

object BuildConfigs extends App {

  implicit val formats = DefaultFormats

  import scala.collection.JavaConverters._

//  ConfigFactory.load().getStringList("teamcity.projects.github").asScala
//    .foreach {
//      p =>
//        Try {
//          val c = Serialization.read[JObject](TcClient.getBuildConfigSteps(p))
////        println(Serialization.writePretty(c))
//          println(p)
//        } getOrElse()
//    }


}
