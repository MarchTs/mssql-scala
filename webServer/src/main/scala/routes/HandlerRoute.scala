package routes

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.{cors, corsRejectionHandler}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import messages.system.ErrorMessage
import utilities.CirceImplicits

object HandlerRoute extends FailFastCirceSupport with CirceImplicits {

  def defaultRejectionHandler: RejectionHandler =
    RejectionHandler
      .newBuilder()
      .handle {
        case MissingCookieRejection(cookieName) =>
          errorResponse(BadRequest, s"No cookies in $cookieName, no service!!!")
      }
      .handle {
        case MalformedRequestContentRejection(msg, _) =>
          errorResponse(BadRequest, msg)
      }
      .handle {
        case RequestEntityExpectedRejection =>
          errorResponse(BadRequest, "support json only")
      }
      .handle {
        case MethodRejection(httpMethod) =>
          errorResponse(BadRequest, s"support method ${httpMethod.value} only")
      }
      .handle {
        case otherRejection =>
          errorResponse(InternalServerError, s"error: ${otherRejection.toString}")
      }
      .result()

  def routeWithCorsWithHandler(route: Route,
                               corsSettings: CorsSettings = CorsSettings.defaultSettings,
                               rejectionHandler: RejectionHandler = defaultRejectionHandler): Route = {

    val rejectionHandlerWithCors = corsRejectionHandler.withFallback(rejectionHandler)

    handleRejections(rejectionHandlerWithCors) {
      cors(corsSettings) {
        handleRejections(rejectionHandlerWithCors) {
          route
        }
      }
    }
  }

  private def errorResponse(code: StatusCode, message: String): StandardRoute =
    complete(code, ErrorMessage(code.intValue, message))

}
