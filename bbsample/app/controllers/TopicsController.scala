package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import repositories._

@Singleton
class TopicsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val topicRepository = new TopicRepository
  val commentRepository = new CommentRepository

  def show(id: Long) = Action { implicit request: Request[AnyContent] =>
    val topic = topicRepository.findBy(TopicId(id))
    val comments = commentRepository.findBy(TopicId(id))

    topic match {
      case Some(topic) => Ok(views.html.Topic.show(topic, comments))
      case None => NotFound
    }
  }
}
