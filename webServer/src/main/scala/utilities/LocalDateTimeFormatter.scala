package utilities

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

object LocalDateTimeFormatter {

  def printDate(date: LocalDateTime): String = date.format(DTF_DATE)

  def printDateTime(date: LocalDateTime): String = date.format(DTF_DATETIME)

  def printDateTimeForFileName(date: LocalDateTime): String = date.format(UPDATE_FILE_DATE)

  def printDateForWeb(date: LocalDateTime): String = date.format(WEB_DATE)

  def printDateTimeForWeb(date: LocalDateTime): String = date.format(WEB_DATE_TIME)

  def printKkDate(date: LocalDateTime): String = date.format(KK_DATE)

  def printKkDateTime(date: LocalDateTime): String = date.format(KK_DATE_TIME)

  def toDate(date: String): LocalDateTime = LocalDate.parse(date, DTF_DATE).atStartOfDay()

  def toDateTimeWithTimeZone(date: String): LocalDateTime = LocalDateTime.parse(date, DTF_DATETIME_TIMEZONE)

  def toDateWithTime(date: String): LocalDateTime = LocalDateTime.parse(date, DTF_DATETIME)

  def toDateFromWeb(date: String): LocalDateTime = LocalDateTime.parse(date, WEB_DATE)

  def toKkDate(date: String): LocalDateTime = LocalDateTime.parse(date, KK_DATE)

  private val DTF_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  private val DTF_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  private val DTF_DATETIME_TIMEZONE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm Z")

  private val WEB_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy")

  private val WEB_DATE_TIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

  private val UPDATE_FILE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")

  private val KK_DATE = DateTimeFormatter.ofPattern("yyyyMMdd")

  private val KK_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")

}
