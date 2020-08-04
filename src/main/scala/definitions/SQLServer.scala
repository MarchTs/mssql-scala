package definitions

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object SQLServer {

  val dbConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig("sqlserver")
  val db: JdbcProfile#Backend#Database = dbConfig.db

}
