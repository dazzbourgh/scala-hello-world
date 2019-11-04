package controllers

import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
//  def index() = Action(parse.json[ClientModels.Request](js => JsSuccess(ClientModels.Request(1L, 2L)))) { request =>
//    Ok(Json.toJson(request.body)(_ => JsObject(mutable.HashMap())))
//  }
  // TODO: add implicit val format to data classes like here: https://github.com/playframework/play-samples/blob/2.7.x/play-scala-rest-api-example/app/v1/post/PostResourceHandler.scala
}
