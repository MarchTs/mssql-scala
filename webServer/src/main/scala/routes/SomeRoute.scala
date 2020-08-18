package routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import cats.data.EitherT
import cats.implicits._
import facades.SomeFacade
import messages.SomeMessage

import scala.concurrent.ExecutionContext

class SomeRoute(someFacade: SomeFacade)(implicit ec: ExecutionContext) extends ApiRoute {

  val route: Route = pathPrefix("v1") {
    concat(
      path("something") {
        (get) {
          val action = someFacade.getFromSomeFormMSSQL()
          handling(EitherT(action).map(SomeMessage.from).value)
        }
      }
    )
  }

}
