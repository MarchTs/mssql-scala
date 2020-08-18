package repositories

import entities.SomeEntity
import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape

class SomeRepository {

  val tableQuery: TableQuery[SomeTables] = TableQuery[SomeTables]

  def get(id: String): DBIO[Option[SomeEntity]] = {

    val action = tableQuery
      .take(1)
      .result
      .headOption
    action
  }

  class SomeTables(tag: Tag) extends Table[SomeEntity](tag, "some_table") {

    def id: Rep[String] = column[String]("id", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name")

    def value: Rep[Int] = column[Int]("value")

    def * : ProvenShape[SomeEntity] =
      (id, name, value) <> (SomeEntity.tupled, SomeEntity.unapply)

  }

}

object SomeRepository extends SomeRepository
