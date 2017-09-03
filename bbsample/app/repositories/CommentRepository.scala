package repositories

import scalikejdbc._
import models._

class CommentRepository {
  def findBy(id: CommentId): Option[Comment] = DB readOnly { implicit session =>
    sql"select * from comments where id = ${id.value}"
      .map(row => Comment(CommentId(row.int("id")), row.string("body")))
      .single
      .apply()
  }

  def findBy(topicId: TopicId): Seq[Comment] = DB readOnly { implicit session=>
    sql"select * from comments where topic_id = ${topicId.value}"
      .map(row => Comment(CommentId(row.int("id")), row.string("body")))
      .list
      .apply()
  }
}
