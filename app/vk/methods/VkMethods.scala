package vk.methods

import play.api.libs.json.{Json, Reads}
import vk.dtos.VkDtos.{Response, StreamingDto}
import vk.dtos.VkModels.Rule
import vk.http.HttpRequests

// TODO: add websocket methods
object VkMethods {

  trait MethodUrl {
    protected def methodUrl: String
  }

  // initialized in StreamingInit class from secret property file
  var accessToken: String = ""
  var apiBaseUrl: String = ""
  var v: String = ""
  lazy implicit val streamingResponse: StreamingDto = Streaming().get[Response[StreamingDto]].response

  abstract private case class Rules() extends HttpRequests with MethodUrl {
    // TODO: get from conf file
    override protected def methodUrl = "rules"

    override protected def url: String = s"https://${streamingResponse.endpoint}/$methodUrl"

  }

  abstract private case class Streaming() extends HttpRequests with MethodUrl {
    // TODO: get from conf file
    override protected def methodUrl: String = "streaming.getServerUrl"

    override protected def url: String = s"$apiBaseUrl/$methodUrl"
  }

  object Rules {
    implicit val reads: Reads[Rule] = Json.format

    def apply(requestParams: Map[String, String] = Map.empty): HttpRequests = new Rules() {
      override def params: Map[String, String] = requestParams + ("key" -> streamingResponse.key)
    }
  }

  object Streaming {
    implicit val reads: Reads[Response[StreamingDto]] = Json.format

    def apply(): HttpRequests = new Streaming {
      override protected def params: Map[String, String] = Map()
    }
  }

}
