package routes

import java.io.File

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.FileInfo
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import definitions.CustomError
import definitions.CustomError.NotFoundData
import io.circe.Encoder
import messages.Message
import messages.system.ErrorMessage
import utilities.CirceImplicits

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait ApiRoute extends FailFastCirceSupport with CirceImplicits with LazyLogging {

  protected val route: Route

  protected def handling[T <: Message](
      action: => Future[Either[CustomError, T]]
  )(implicit encoder: Encoder[T]): Route = onComplete(action) {
    case Success(Right(message)) => complete(message)
    case Success(Left(error))    => handlingError(error)
    case Failure(throwable)      => handlingThrowable(throwable)
  }

  private def handlingError(customError: CustomError): StandardRoute = customError match {
    case NotFoundData => error(NotFound, CustomError.toErrorString(NotFoundData))
    case otherError   => error(BadRequest, CustomError.toErrorString(otherError))
  }

  private def handlingThrowable(throwable: Throwable): StandardRoute = {
    logger.error("Unexpected error occurred", throwable)
    error(InternalServerError, "Something went wrong on our server, please contact us.")
  }

  private def error(code: StatusCode, message: String): StandardRoute =
    complete(code, ErrorMessage(code.intValue, message))

}
