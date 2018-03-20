package pl.github.domain.services.http

import io.netty.handler.codec.http.HttpMethod
import org.asynchttpclient.RequestBuilder
import org.asynchttpclient.util.HttpConstants.Methods
import org.json4s.Formats
import org.json4s.native.Serialization

import scala.collection.mutable

object Pageable {

  def apply(response: => GhResponse)(implicit formats: Formats, ghHttp: GhHttpConnector): List[String] = {
    val buffer = mutable.MutableList.empty[String]
    var currentResponseOpt = Option(response)
    do {
      currentResponseOpt = for {
        currentResponse <- currentResponseOpt
        _ = buffer += currentResponse.body
        link <- currentResponse.link
        next <- link.next
      } yield {
        ghHttp.executeRequest(new RequestBuilder().setMethod(Methods.GET).setUrl(next))
      }
    } while (currentResponseOpt.isDefined)
    buffer.toList
  }

}
