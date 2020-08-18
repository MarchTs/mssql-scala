package routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import definitions.system.SystemDefinition._
import messages.system.SystemStatusMessage
import utilities.CirceImplicits._
import io.circe.syntax._

class SystemRoute {

  val route: Route = pathPrefix("system") {
    concat(
      path("version") {
        get {
          complete(SystemStatusMessage(SERVER_VERSION, SERVER_NAME).asJson.noSpaces)
        }
      }
    )
  }

}
