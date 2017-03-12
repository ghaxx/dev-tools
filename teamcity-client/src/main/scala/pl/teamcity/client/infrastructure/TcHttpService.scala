package pl.teamcity.client.infrastructure

import com.typesafe.scalalogging.LazyLogging
import io.netty.handler.codec.http.HttpHeaders
import io.netty.handler.ssl.{SslContextBuilder, SslProvider}
import org.asynchttpclient.Realm.AuthScheme
import org.asynchttpclient.netty.ssl.InsecureTrustManagerFactory
import org.asynchttpclient.{DefaultAsyncHttpClient, DefaultAsyncHttpClientConfig, Realm}
import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, JObject}
import pl.teamcity.client.TcConfig

import scala.concurrent.{Await, Future}

class TcHttpService(config: TcConfig) extends LazyLogging {

  import TcHttpService._
  import pl.http.client.ScalaAsyncHttpClient._

  private implicit val formats = DefaultFormats

  private lazy val clientConfig = {
    val builder = new DefaultAsyncHttpClientConfig.Builder()
    val sslContext =
      SslContextBuilder
        .forClient()
        .sslProvider(SslProvider.JDK)
        .trustManager(InsecureTrustManagerFactory.INSTANCE)
        .build()

    builder
      .setSslContext(sslContext)
      .build()
  }
  private val client = new DefaultAsyncHttpClient(clientConfig)

  private val baseUri = config.uri
  private val user = config.user
  private val password = config.password

  def get(uri: String): JObject = {
    logger.info(s"Get URL ${fullUrl(uri)}")
    val responseStr = client
      .prepareGet(fullUrl(uri))
      .setHeader(HttpHeaders.Names.ACCEPT, HttpHeaders.Values.APPLICATION_JSON)
      .setRealm(new Realm.Builder(user, password).setScheme(AuthScheme.BASIC))
      .asyncExecuteAsString()
      .futureValue
    logger.debug(responseStr)
    try {
      Serialization.read[JObject](responseStr)
    } catch {
      case e: Exception =>
        println(responseStr)
        logger.error(s"Cannot parse: $responseStr")
        throw e
    }
  }

  def post(uri: String, data: String): JObject = {
    logger.info(s"Post URL ${fullUrl(uri)}")
    val responseStr = client
      .preparePost(fullUrl(uri))
      .setBody(data)
      .setHeader(HttpHeaders.Names.ACCEPT, HttpHeaders.Values.APPLICATION_JSON)
      .setHeader(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_JSON)
      .setRealm(new Realm.Builder(user, password).setScheme(AuthScheme.BASIC))
      .asyncExecuteAsString()
      .futureValue
    Serialization.read[JObject](responseStr)
  }

  def close() = client.close()

  private def fullUrl(uri: String) = s"$baseUri$uri"

}

object TcHttpService {

  import scala.concurrent.duration._

  implicit class F[T](val f: Future[T]) extends AnyVal {
    def futureValue: T = {
      Await.result(f, 10 seconds)
    }
  }

}
