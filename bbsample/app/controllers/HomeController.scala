package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import repositories._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(messagesAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {
  val topicRepository = new TopicRepository

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    val topics = topicRepository.findAll
    val topicForm = Form(
      mapping(
        "title" -> text,
        "body"  -> text
      )(TopicData.apply)(TopicData.unapply)
    )

    Ok(views.html.index(topics, topicForm))
  }
}

case class TopicData(title: String, body: String)
