package controllers

import javax.inject._
import models.ClientModels
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action(parse.json[ClientModels.Request]) { request =>
    Ok(Json.toJson(request.body))
  }
}
