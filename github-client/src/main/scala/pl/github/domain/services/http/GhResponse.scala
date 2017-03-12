package pl.github.domain.services.http

import pl.github.domain.services.http.GhResponse.Link

case class GhResponse(
  body: String,
  status: Int,
  headers: Map[String, String]
)(private val httpClient: GhHttpConnector) {
  lazy val link: Option[Link] = headers.get("Link").map(Link(_))
}

object GhResponse {
  case class Link(
    first: Option[String],
    last: Option[String],
    prev: Option[String],
    next: Option[String]
  )
  object Link {
    private val RelRegexp = """<(.*)>; rel="(.*)"""".r
    def apply(rawValue: String): Link = {
      val rels = rawValue.split(""",\s*""")
      val links = {
        rels.map {
          case RelRegexp(url, rel) => (rel, Some(url))
        }.toMap withDefaultValue None
      }
      new Link(links("first"), links("last"), links("prev"), links("next"))
    }
  }
}