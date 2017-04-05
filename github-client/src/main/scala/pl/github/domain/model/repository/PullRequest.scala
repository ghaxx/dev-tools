package pl.github.domain.model.repository

import org.json4s.{DefaultFormats, JValue}

trait PullRequest {
  def htmlUrl: String
}

class PreloadedPullRequest(json: JValue) extends PullRequest {
  private implicit val formats = DefaultFormats

  override def htmlUrl = (json \ "html_url").extract[String]
}