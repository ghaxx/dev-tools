package pl.teamcity.client

import com.typesafe.config.{Config, ConfigFactory}

case class TcConfig(
  uri: String,
  user: String,
  password: String
)

object TcConfig {
  def fromFile: TcConfig = {
    val config = ConfigFactory.load()
    new TcConfig(
      config.getString("teamcity.uri"),
      config.getString("teamcity.user"),
      config.getString("teamcity.password")
    )
  }
}