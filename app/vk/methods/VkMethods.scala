package vk.methods

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import com.typesafe.config.ConfigFactory
import models.Event
import play.api.libs.json.{Json, Reads}
import vk.dtos.VkDtos.{EventCodeResponseDto, Response, StreamingDto}
import vk.dtos.VkModels.Rule
import vk.http.HttpRequests

import scala.concurrent.{Future, Promise}

object VkMethods {
  private val config = ConfigFactory.load("application.conf")

  var accessToken: String = config.getString("vk.accessToken")
  var apiBaseUrl: String = config.getString("vk.apiBaseUrl")
  var v: String = config.getString("vk.v")

  trait MethodUrl {
    protected def methodUrl: String
  }

  lazy implicit val streamingResponse: StreamingDto = Streaming().get[Response[StreamingDto]].response

  abstract private case class Rules() extends HttpRequests with MethodUrl {
    override protected def methodUrl: String = config.getString("vk.rulesPath")

    override protected def url: String = s"https://${streamingResponse.endpoint}/$methodUrl"
  }

  abstract private case class Streaming() extends HttpRequests with MethodUrl {
    override protected def methodUrl: String = config.getString("vk.streamingPath")

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

    def openConnection(processEvent: Event => Unit): Promise[Option[Message]] = {
      implicit val system: ActorSystem = ActorSystem()
      implicit val materializer: ActorMaterializer = ActorMaterializer()

      val sink: Sink[Message, Future[Done]] =
        Sink.foreach[Message] {
          case message: TextMessage.Strict =>
            val event = Json.parse(message.text).validate[EventCodeResponseDto].map(_.event).get
            processEvent(event)
        }

      val flow: Flow[Message, Message, Promise[Option[Message]]] =
        Flow.fromSinkAndSourceMat(
          sink,
          Source.maybe[Message])(Keep.right)

      val (_, closingPromise) =
        Http().singleWebSocketRequest(
          WebSocketRequest(s"wss://${streamingResponse.endpoint}/stream?key=${streamingResponse.key}"),
          flow)

      closingPromise
    }
  }

}
