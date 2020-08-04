package definitions

sealed trait CustomError

object CustomError {

  case object CreateFail extends CustomError

  case object UpdateFail extends CustomError

  case object DeleteFail extends CustomError

  case object ValidationFail extends CustomError

  case object NotFoundData extends CustomError

  case class OtherError(msg: String) extends CustomError

  def toErrorString(error: CustomError): String =
    error match {
      case CreateFail      => "create failed"
      case UpdateFail      => "update failed"
      case DeleteFail      => "delete failed"
      case ValidationFail  => "invalid content"
      case NotFoundData    => "not found data"
      case OtherError(msg) => msg
    }

}
