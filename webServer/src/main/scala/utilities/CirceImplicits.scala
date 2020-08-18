package utilities

import java.time.LocalDateTime

import io.circe.Decoder.Result
import io.circe.generic.extras.{AutoDerivation, Configuration}
import io.circe.{Decoder, DecodingFailure, Encoder, HCursor}

trait CirceImplicits
  extends AutoDerivation
    with ConfigurationImplicits
    with LocalDateTimeImplicits

object CirceImplicits extends CirceImplicits

private[utilities] trait ConfigurationImplicits {

    implicit val configuration: Configuration = Configuration.default

}

private[utilities] trait LocalDateTimeImplicits {

  implicit val localDateTimeEncoder: Encoder[LocalDateTime] = new Encoder[LocalDateTime] {
    override def apply(dateTime: LocalDateTime): io.circe.Json = io.circe.Json.fromString(
      LocalDateTimeFormatter.printDateTime(dateTime)
    )
  }

  implicit val localDateTimeDecoder: Decoder[LocalDateTime] = new Decoder[LocalDateTime] {
    override def apply(cursor: HCursor): Result[LocalDateTime] = cursor.value.asString match {
      case Some(dateTime) => Right(LocalDateTimeFormatter.toDateWithTime(dateTime))
      case None => Left(DecodingFailure("java.time.LocalDateTime", cursor.history))
    }
  }

}
