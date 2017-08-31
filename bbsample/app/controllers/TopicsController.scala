package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._

@Singleton
class TopicsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def show(id: Long) = Action { implicit request: Request[AnyContent] =>
    val topic = Topic(1, "タイトル", "ないようないよう")
    val comments = Seq(Comment(1, "コメント1"), Comment(2, "コメント2"))
    Ok(views.html.Topic.show(topic, comments))
  }
}
