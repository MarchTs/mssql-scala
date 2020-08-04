package repositories

import definitions.SQLServer.db
import entities.SomeTableEntity
import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

class SomeTableRepository {

  val tableQuery: TableQuery[SomeTables] = TableQuery[SomeTables]

  def get(id: Int): Future[Option[SomeTableEntity]] =
    db.run(
      tableQuery
        .take(1)
        .result
        .headOption
    )

  class SomeTables(tag: Tag) extends Table[SomeTableEntity](tag, "some_table") {

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name")

    def value: Rep[Int] = column[Int]("value")

    def * : ProvenShape[SomeTableEntity] =
      (id, name, value) <> (SomeTableEntity.tupled, SomeTableEntity.unapply)

  }

}
