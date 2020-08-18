package utilities

import java.util.UUID

object UuidUtil {

  def getUuid: String = UUID.randomUUID().toString

  def getId: String = getUuid.replace("-", "")

}
