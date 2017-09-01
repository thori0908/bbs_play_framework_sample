package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._

@Singleton
class TopicsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def show(id: Long) = Action { implicit request: Request[AnyContent] =>
    val topic = Topic(TopicId(1), "タイトル", "ないようないよう")
    val comments = Seq(
      Comment(CommentId(1), "コメント1"),
      Comment(CommentId(2), "コメント2")
    )
    Ok(views.html.Topic.show(topic, comments))
  }
}
