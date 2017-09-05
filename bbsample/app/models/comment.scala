package models

case class Comment(
  id: CommentId,
  body: String,
  topicId: TopicId
)

case class CommentId(value: Long)
