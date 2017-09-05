package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import repositories._

@Singleton
class CommentsController @Inject()(messagesAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {
  val commentRepository = new CommentRepository

  private val commentForm = Form(tuple("body" -> text, "topicId" -> number))

  def create = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    val (body, topicId) = commentForm.bindFromRequest.get
    val comment = commentRepository.create(body, TopicId(topicId))
    Redirect(routes.TopicsController.show(comment.topicId.value))
  }
}
