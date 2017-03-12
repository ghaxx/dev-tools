package pl.github.domain.services.http

import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.RepoUpdateData

class GhMyUserHttpClient(implicit ghHttp: GhHttpConnector) {
  implicit val formats = DefaultFormats

  def getMyDetails: JValue = {
    val url = s"/user"
    Serialization.read[JValue](ghHttp.getRequest(url).body)
  }

  def getRepos: List[JValue] = {
    val url = s"/user/repos"
    Pageable(ghHttp.getRequest(url)).flatMap(Serialization.read[List[JValue]])
  }
}
