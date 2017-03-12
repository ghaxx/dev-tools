package pl.github.domain.services.http

import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, JValue}
import pl.github.domain.model.RepoUpdateData

class GhOrgHttpClient(implicit ghHttp: GhHttpConnector)  {
  implicit val formats = DefaultFormats

  def getDetails(login: String): JValue = {
    val url = s"/orgs/$login"
    Serialization.read[JValue](ghHttp.getRequest(url).body)
  }

  def getRepos(owner: String): List[JValue] = {
    val url = s"/orgs/$owner/repos"
    Pageable(ghHttp.getRequest(url)).flatMap(Serialization.read[List[JValue]])
  }

}
