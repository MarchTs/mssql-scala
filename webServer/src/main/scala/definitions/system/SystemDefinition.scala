package definitions.system

import utilities.Config

object SystemDefinition {

  val SERVER_VERSION: String = Config("version")

  val SERVER_NAME: String = Config("name")

}
