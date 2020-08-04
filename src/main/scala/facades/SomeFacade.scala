package facades

import com.typesafe.scalalogging.LazyLogging
import definitions.CustomError
import entities.SomeEntity
import repositories.SomeRepository
import definitions.SQLServer.db

import scala.concurrent.{ExecutionContext, Future}

class SomeFacade (implicit ec: ExecutionContext) extends LazyLogging{

  def getFromSomeFormMSSQL(): Future[Either[CustomError, SomeEntity]] = {
    val action = SomeRepository.get(1)
    db.run(action).map {
      case Some(value) => Right(value)
      case None => Left(CustomError.NotFoundData)
    }
  }
}
