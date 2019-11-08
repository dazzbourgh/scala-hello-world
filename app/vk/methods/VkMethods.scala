package vk.methods

import play.api.http.{ContentTypes, HeaderNames}
import play.api.libs.json.{Json, Reads}
import scalaj.http.Http
import vk.dtos.VkDtos.{Response, RuleDto, RulesDto, StreamingDto}
import vk.dtos.VkModels.Rule

trait Exchanging {

  import vk.methods.VkMethods.{accessToken, v}

  def exchange[Req, Resp](url: String,
                          params: Map[String, String] = Map.empty,
                          body: Option[Req] = None)
                         (implicit reads: Reads[Resp]): Resp = {
    var req = Http(url)
      .header(HeaderNames.CONTENT_TYPE, ContentTypes.JSON)
      .param("v", v)
      .param("access_token", accessToken)
    for (entry <- params) {
      req = req.param(entry._1, entry._2)
    }
    if (body.nonEmpty) req = req.postData(body.get.toString)
    val response = req.asString.body
    val jsValue = Json.parse(response)
    jsValue.validate[Resp].get
  }
}

trait HasUrl {
  def url: String
}

trait HttpRequests extends Exchanging {
  def get()
  def post()
  def delete()
}

object VkMethods {
  // TODO: get from conf file
  var accessToken: String = "2f7602712f7602712f760271652f1bba8022f762f76027172ccb995673d74857877e22b"
  var apiBaseUrl: String = "https://api.vk.com/method"
  var v: String = "5.103"
  lazy implicit val streamingResponse: StreamingDto = Streaming()

  case class Rules(body: Rule)

  case class Streaming()

  object Streaming extends Exchanging with HasUrl {
    // TODO: get from conf file
    val url: String = "streaming.getServerUrl"
    implicit val reads: Reads[Response[StreamingDto]] = Json.format

    def apply(): StreamingDto = exchange(s"$apiBaseUrl/$url")
      .response
  }

  object Rules extends Exchanging with HasUrl {
    // TODO: get from conf file
    val url: String = "rules"
    implicit val reads: Reads[Rule] = Json.format

    def apply(): RulesDto = {
      exchange[Rule, RulesDto](s"https://${streamingResponse.endpoint}/rules",
        Map("key" -> streamingResponse.key))
    }

    def apply(body: Rule): Rule = {
      exchange(s"https://${streamingResponse.endpoint}/rules",
        Map("key" -> streamingResponse.key),
        Some(RuleDto(body)))
    }
  }

  def main(args: Array[String]): Unit = {
//    val rules = Rules(Rule("трамп", "1"))
//    println(rules)
    println(Rules())
  }
}
