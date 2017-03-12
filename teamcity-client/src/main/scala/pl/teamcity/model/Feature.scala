package pl.teamcity.model

case class Feature(
  id: String,
  `type`: String,
  properties: Feature.Properties
)

object Feature {
  case class Properties(property: List[Property])

  case class Property(
    name: String,
    value: Option[String]
  )
}