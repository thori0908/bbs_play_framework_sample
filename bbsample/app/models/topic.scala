package models

case class Topic(
  id: TopicId,
  title: String,
  body: String
)

case class TopicId(value: Long)
