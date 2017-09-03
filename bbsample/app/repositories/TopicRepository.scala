package repositories

import scalikejdbc._
import models._

class TopicRepository {
  def findBy(id: TopicId): Option[Topic] = DB readOnly { implicit session =>
    sql"select * from topics where id = ${id.value}"
      .map(row => Topic(TopicId(row.int("id")), row.string("title"), row.string("body")))
      .single
      .apply()
  }

  def findAll: Seq[Topic] = DB readOnly { implicit session=>
    sql"select * from topics"
      .map(row => Topic(TopicId(row.int("id")), row.string("title"), row.string("body")))
      .list
      .apply()
  }
}
