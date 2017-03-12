package pl.github.domain.model

case class GitHubKey(
  id: GitHubKey.Id,
  key: String,
  url: String,
  title: String,
  verified: Boolean,
  created_at: String,
  read_only: Boolean
) {
  def delete = ???
}

object GitHubKey {
  case class Id(value: Int) extends AnyVal
}
