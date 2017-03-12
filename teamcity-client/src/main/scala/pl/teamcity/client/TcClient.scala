package pl.teamcity.client

import com.typesafe.config.ConfigFactory
import io.netty.handler.codec.http.HttpHeaders
import io.netty.handler.ssl.{SslContextBuilder, SslProvider}
import org.asynchttpclient.Realm.AuthScheme
import org.asynchttpclient.{DefaultAsyncHttpClient, DefaultAsyncHttpClientConfig, Realm}
import org.asynchttpclient.netty.ssl.InsecureTrustManagerFactory
import org.json4s.JsonAST.JObject
import pl.teamcity.client.infrastructure.TcHttpService
import pl.teamcity.model.creation.BuildStep

import scala.concurrent.{Await, Future}

class TcClient(httpClient: TcHttpService) {

  val buildTypes = new TcBuildTypesClient(httpClient)
  val vcsRoots = new TcVcsRootsClient(httpClient)

  def close() = httpClient.close()

}

object TcClient {
  def withConfig(config: TcConfig) = new TcClient(new TcHttpService(config))
  lazy val default = withConfig(TcConfig.fromFile)
}