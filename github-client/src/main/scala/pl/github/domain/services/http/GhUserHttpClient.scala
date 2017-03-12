package pl.github.domain.services.http

import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, JValue}

class GhUserHttpClient(implicit ghHttp: GhHttpConnector) {
  implicit val formats = DefaultFormats

  def getDetails(login: String): JValue = {
    val url = s"/users/$login"
    Serialization.read[JValue](ghHttp.getRequest(url).body)
  }

  def getRepos(owner: String): List[JValue] = {
    val url = s"/users/$owner/repos"
    Pageable(ghHttp.getRequest(url)).flatMap(Serialization.read[List[JValue]])
  }
}
