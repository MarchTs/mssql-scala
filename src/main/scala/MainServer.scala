import akka.Done
import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.LazyLogging
import definitions.system.SystemDefinition
import facades.SomeFacade
import routes.{HandlerRoute, SomeRoute, SystemRoute}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.language._


object MainServer extends LazyLogging {

  implicit val system: ActorSystem                        = ActorSystem(SystemDefinition.SERVER_NAME)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {
    val shut          = CoordinatedShutdown(system)
    val someFacade = new SomeFacade()
    val systemRoute   = new SystemRoute().route
    val someRoute = new SomeRoute(someFacade).route

    val routes: Route = HandlerRoute.routeWithCorsWithHandler(
      systemRoute ~ someRoute
    )

    (for {
      bind <- Http().bindAndHandle(routes, "0.0.0.0", 9000)
    } yield (bind, shut)).foreach {
      case (binding, shutdown) =>
        logger.info("start server")

        shutdown.addTask(
          CoordinatedShutdown.PhaseServiceUnbind,
          "http-unbind"
        ) { () =>
          logger.info("unbind")
          binding.unbind().map(_ => Done)
        }

        shutdown.addTask(
          CoordinatedShutdown.PhaseServiceRequestsDone,
          "http-graceful-terminate"
        ) { () =>
          logger.info("terminate")
          binding.terminate(10.seconds).map(_ => Done)
        }

        shutdown.addTask(
          CoordinatedShutdown.PhaseServiceStop,
          "http-shutdown"
        ) { () =>
          logger.info("shutdown")
          Http().shutdownAllConnectionPools().map(_ => Done)
        }
    }
  }

}

