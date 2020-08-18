package utilities

import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.FiniteDuration
import scala.jdk.CollectionConverters._
import scala.util.Try

object Config {

  private val config = ConfigFactory.load()

  def apply(key: String): String = config.getString(key)

  def apply(key: String, default: String): String = Try(config.getString(key)).getOrElse(default)

  def bool(key: String): Boolean = config.getBoolean(key)

  def bool(key: String, default: Boolean): Boolean = Try(config.getBoolean(key)).getOrElse(default)

  def duration(key: String, default: FiniteDuration): FiniteDuration = Try(config.getDuration(key)) map { d =>
    FiniteDuration(d.toNanos, TimeUnit.NANOSECONDS)
  } getOrElse default

  def stringList(key: String): List[String] = config.getStringList(key).asScala.toList

}
