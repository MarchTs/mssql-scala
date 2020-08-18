package services

import com.typesafe.scalalogging.LazyLogging
import definitions.CustomError
import definitions.SQLServer.db
import entities.SomeEntity
import repositories.SomeRepository

import scala.concurrent.{ExecutionContext, Future}

class SomeService extends LazyLogging {

  def get(id: String)(implicit ec: ExecutionContext): Future[Either[CustomError, SomeEntity]] = {
    val action = SomeRepository.get(id)
    db.run(action).map {
      case Some(value) => Right(value)
      case None        => Left(CustomError.NotFoundData)
    }
  }

}

object SomeService extends SomeService
