package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import repositories._

@Singleton
class TopicsController @Inject()(messagesAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {
  val topicRepository = new TopicRepository
  val commentRepository = new CommentRepository

  private val topicForm = Form(tuple("title" -> text, "body" -> text))

  def show(id: Long) = Action { implicit request: Request[AnyContent] =>
    val topic = topicRepository.findBy(TopicId(id))
    val comments = commentRepository.findBy(TopicId(id))

    topic match {
      case Some(topic) => Ok(views.html.Topic.show(topic, comments))
      case None => NotFound
    }
  }

  def create = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    val (title, body) = topicForm.bindFromRequest.get
    val topic = topicRepository.create(title, body)
    Redirect(routes.HomeController.index)
  }
}
