package pl.github.domain.services.http

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import io.netty.handler.codec.http.HttpHeaders
import io.netty.handler.ssl.{SslContextBuilder, SslProvider}
import org.asynchttpclient.netty.ssl.InsecureTrustManagerFactory
import org.asynchttpclient.{DefaultAsyncHttpClient, DefaultAsyncHttpClientConfig}
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JObject
import org.json4s.native.Serialization
import pl.github.domain.model.{GitHubKey, GitHubKeyCreationData, RepoUpdateData}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class GitHubHttpClient extends LazyLogging with AutoCloseable {

  private val connector = new GhHttpConnector

  val myUser = new GhMyUserHttpClient()(connector)
  val user = new GhUserHttpClient()(connector)
  val org = new GhOrgHttpClient()(connector)
  val repo = new GhRepositoryHttpClient(connector)

  def close(): Unit = connector.close()
}

