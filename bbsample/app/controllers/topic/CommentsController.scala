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
  val topicRepository = new TopicRepository
  val commentRepository = new CommentRepository

  private val commentForm = Form(
    mapping("body" -> nonEmptyText)(CommentData.apply)(CommentData.unapply)
  )

  def create(topicId: Long) = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    commentForm.bindFromRequest.fold(
      formWithErrors => {
        val topic = topicRepository.findBy(TopicId(topicId))
        val comments = commentRepository.findBy(TopicId(topicId))
        topic match {
          case Some(topic) => BadRequest(views.html.Topic.show(topic, comments, formWithErrors))
          case None => Redirect(routes.HomeController.index)
        }
      },
      commentData => {
        val comment = commentRepository.create(commentData.body, TopicId(topicId))
        Redirect(routes.TopicsController.show(topicId))
      }
    )
  }
}
