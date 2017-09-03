package models

case class Comment(
  id: CommentId,
  body: String
)

case class CommentId(value: Long)
