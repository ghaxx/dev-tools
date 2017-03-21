package pl.teamcity.model

import pl.teamcity.client.TcClient

class VcsRoot(id: String, client: TcClient) {
  def details = client.vcsRoots.get(id)
}
