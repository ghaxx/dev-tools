package pl.github.domain.services.http

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.{LazyLogging, Logger}
import io.netty.handler.codec.http.HttpHeaders
import io.netty.handler.ssl.{SslContextBuilder, SslProvider}
import org.asynchttpclient.netty.ssl.InsecureTrustManagerFactory
import org.asynchttpclient.util.HttpConstants.Methods
import org.asynchttpclient.{DefaultAsyncHttpClient, DefaultAsyncHttpClientConfig, HttpResponseStatus, Request, RequestBuilder, Response}

import scala.concurrent.{Await, Future}

class GhHttpConnector extends LazyLogging with AutoCloseable {

  import GhHttpConnector._
  import pl.http.client.ScalaAsyncHttpClient._

  private val config = ConfigFactory.load()

  private val token = config.getString("github.access-token")
  private val baseUri = config.getString("github.uri")

  private val clientConfig = {
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

  def getRequest(url: String) = {
    val fullUrl = buildFullUrl(url)
    executeRequest(new RequestBuilder()
      .setMethod(Methods.GET)
      .setUrl(fullUrl)
      .setHeader(HttpHeaders.Names.ACCEPT, HttpHeaders.Values.APPLICATION_JSON)
      .build())
  }

  def postRequest(url: String, data: String, accept: String = HttpHeaders.Values.APPLICATION_JSON) = {
    val fullUrl = buildFullUrl(url)
    executeRequest(new RequestBuilder()
      .setMethod(Methods.POST)
      .setUrl(fullUrl)
      .setBody(data)
      .setHeader(HttpHeaders.Names.ACCEPT, accept)
      .build())
  }

  def patchRequest(url: String, data: String, accept: String = HttpHeaders.Values.APPLICATION_JSON) = {
    val fullUrl = buildFullUrl(url)
    executeRequest(new RequestBuilder()
      .setMethod(Methods.PATCH)
      .setUrl(fullUrl)
      .setBody(data)
      .setHeader(HttpHeaders.Names.ACCEPT, accept)
      .build())
  }

  def putRequest(url: String, data: String, accept: String = HttpHeaders.Values.APPLICATION_JSON) = {
    val fullUrl = buildFullUrl(url)
    executeRequest(new RequestBuilder()
      .setMethod(Methods.PUT)
      .setUrl(fullUrl)
      .setBody(data)
      .setHeader(HttpHeaders.Names.ACCEPT, accept)
      .build())
  }

  def deleteRequest(url: String, data: String = null, accept: String = HttpHeaders.Values.APPLICATION_JSON) = {
    val fullUrl = buildFullUrl(url)
    executeRequest(new RequestBuilder()
      .setMethod(Methods.DELETE)
      .setUrl(fullUrl)
      .setBody(data)
      .setHeader(HttpHeaders.Names.ACCEPT, accept)
      .build())
  }

  private def buildFullUrl(url: String) = {
    s"$baseUri${url}?access_token=${token}"
  }

  def executeRequest(request: Request): GhResponse = {
    Loggers.url.info(s"${request.getMethod} url ${request.getUrl}")
    Loggers.data.info(request.getStringData)
    val response = client.asyncExecute(request).futureValue
    if (response.getStatusCode < 200 || response.getStatusCode >= 300) {
      throw new RuntimeException(s"Error with request ${request.getUrl}: ${response.getStatusCode} - ${response.getStatusText} - ${response.getResponseBody}")
    } else {
      Loggers.response.info(response.toString)
      import scala.collection.JavaConverters._
      GhResponse(
        body = response.getResponseBody,
        status = response.getStatusCode,
        headers = response.getHeaders.entries().asScala.map(x => (x.getKey, x.getValue)).toMap withDefaultValue ""
      )(this)
    }
  }

  def close(): Unit = client.close()
}

object GhHttpConnector {

  import scala.concurrent.duration._

  implicit class F[T](val f: Future[T]) extends AnyVal {
    def futureValue: T = {
      Await.result(f, 10 seconds)
    }
  }

  private object Loggers {
    val url = Logger("client.github.request.url")
    val data = Logger("client.github.request.data")
    val response = Logger("client.github.request.response")
  }
}