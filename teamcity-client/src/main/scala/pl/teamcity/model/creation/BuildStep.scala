package pl.teamcity.model.creation

case class BuildStep(stepId: String) {
  lazy val json =
    s"""
       |{
       |  "id":"$stepId",
       |  "name":"Build",
       |  "type":"simpleRunner",
       |  "properties":{
       |    "property":[
       |      {
       |        "name":"command.executable",
       |        "value":"bash"
       |      },
       |      {
       |        "name":"command.parameters",
       |        "value":"ls"
       |      },
       |      {
       |        "name":"teamcity.step.mode",
       |        "value":"default"
       |      }
       |    ]
       |  }
       |}
      """.stripMargin
}
