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

  private val topicForm = Form(
    mapping(
      "title" -> nonEmptyText(minLength = 10, maxLength = 20),
      "body"  -> nonEmptyText
    )(TopicData.apply)(TopicData.unapply)
  )

  def show(id: Long) = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    val topic = topicRepository.findBy(TopicId(id))
    val comments = commentRepository.findBy(TopicId(id))
    val commentForm = Form(
      mapping("body" -> nonEmptyText)(CommentData.apply)(CommentData.unapply)
    )

    topic match {
      case Some(topic) => Ok(views.html.Topic.show(topic, comments, commentForm))
      case None => NotFound
    }
  }

  def create = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    topicForm.bindFromRequest.fold(
      formWithErrors => {
        val topics = topicRepository.findAll
        BadRequest(views.html.index(topics, formWithErrors))
      },
      commentData => {
        val topic = topicRepository.create(commentData.title, commentData.body)
        Redirect(routes.HomeController.index)
      }
    )
  }
}

case class CommentData(body: String)
