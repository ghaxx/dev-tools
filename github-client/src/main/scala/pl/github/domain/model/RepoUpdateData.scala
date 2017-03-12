package pl.github.domain.model

case class RepoUpdateData(
  name: String,
  description: Option[String] = None,
  homepage: Option[String] = None,
  `private`: Option[Boolean] = None,
  has_issues: Option[Boolean] = None,
  has_wiki: Option[Boolean] = None,
  default_branch: Option[String] = None
)
