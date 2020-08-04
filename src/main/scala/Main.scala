import repositories.SomeRepository

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main {

  implicit val ec: ExecutionContext = ExecutionContext.global

  def main(args: Array[String]): Unit = {
//
//    println(s"Start making connection")
//    val repo = new SomeRepository
//    repo
//      .get(1)
//      .onComplete {
//        case Failure(exception) =>
//          println("error", exception)
//        case Success(value) =>
//          println("success", value)
//      }
    Thread.sleep(2000)
  }

}
