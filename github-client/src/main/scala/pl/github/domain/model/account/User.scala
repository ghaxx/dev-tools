package pl.github.domain.model.account

trait User extends Account {
  def name: User.Name
}
object User {
  case class Name(value: String) extends AnyVal
}