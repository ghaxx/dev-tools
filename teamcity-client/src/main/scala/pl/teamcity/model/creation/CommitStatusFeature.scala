package pl.teamcity.model.creation

case class CommitStatusFeature(vcsRootId: String) {
  lazy val json =
    s"""
       {
         "type":"commit-status-publisher",
         "properties":{
           "property":[
             {
               "name":"github_authentication_type",
               "value":"token"
             },
             {
               "name":"github_host",
               "value":"https://alm-github.systems.uk.hsbc/api/v3"
             },
             {
               "name":"publisherId",
               "value":"githubStatusPublisher"
             },
             {
               "name":"secure:github_access_token",
               "value":"4950552c3e728476da082289495b0180404b9faf"
             },
             {
               "name":"vcsRootId",
               "value":"$vcsRootId"
             }
           ]
         }
       }
     """
}
