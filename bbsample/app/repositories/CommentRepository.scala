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

  def create(body: String)(implicit session: DBSession = AutoSession): Comment = {
    val id = sql"insert into comments (body) values (${body})"
      .updateAndReturnGeneratedKey
      .apply()

    Comment(CommentId(id), body)
  }
}
