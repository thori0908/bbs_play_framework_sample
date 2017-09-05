package repositories

import scalikejdbc._
import models._

class CommentRepository {
  def findBy(id: CommentId): Option[Comment] = DB readOnly { implicit session =>
    sql"select * from comments where id = ${id.value}"
      .map(row => Comment(CommentId(row.int("id")), row.string("body"), TopicId(row.int("topic_id"))))
      .single
      .apply()
  }

  def findBy(topicId: TopicId): Seq[Comment] = DB readOnly { implicit session=>
    sql"select * from comments where topic_id = ${topicId.value}"
      .map(row => Comment(CommentId(row.int("id")), row.string("body"), TopicId(row.int("topic_id"))))
      .list
      .apply()
  }

  def create(body: String, topicId: TopicId)(implicit session: DBSession = AutoSession): Comment = {
    val id = sql"insert into comments (body, topic_id) values (${body}, ${topicId.value})"
      .updateAndReturnGeneratedKey
      .apply()

    Comment(CommentId(id), body, topicId)
  }
}
