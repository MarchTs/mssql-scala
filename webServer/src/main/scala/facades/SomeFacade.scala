package facades

import com.typesafe.scalalogging.LazyLogging
import definitions.CustomError
import entities.SomeEntity
import repositories.SomeRepository
import definitions.SQLServer.db
import services.SomeService

import scala.concurrent.{ExecutionContext, Future}

class SomeFacade (implicit ec: ExecutionContext) extends LazyLogging{

  def getFromSomeFormMSSQL(): Future[Either[CustomError, SomeEntity]] = {
    SomeService.get("1")
  }
}
