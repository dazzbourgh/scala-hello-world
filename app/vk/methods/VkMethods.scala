package vk.methods

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
import play.api.libs.json.{Json, Reads}
import vk.dtos.VkDtos.{Response, StreamingDto}
import vk.dtos.VkModels.Rule
import vk.http.HttpRequests

import scala.concurrent.Future

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

    def openConnection = {
      implicit val system = ActorSystem()
      implicit val materializer = ActorMaterializer()
      import system.dispatcher

      val incoming: Sink[Message, Future[Done]] =
        Sink.foreach[Message] {
          case message: TextMessage.Strict =>
            println(message.text)
        }

      // send this as a message over the WebSocket
      val outgoing = Source.single(TextMessage("hello world!"))

      // flow to use (note: not re-usable!)
      val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest(s"wss://${
        streamingResponse
          .endpoint
      }/stream?key=${streamingResponse.key}"))

      // the materialized value is a tuple with
      // upgradeResponse is a Future[WebSocketUpgradeResponse] that
      // completes or fails when the connection succeeds or fails
      // and closed is a Future[Done] with the stream completion from the incoming sink
      val (upgradeResponse, closed) =
      outgoing
        .viaMat(webSocketFlow)(Keep.right) // keep the materialized Future[WebSocketUpgradeResponse]
        .toMat(incoming)(Keep.both) // also keep the Future[Done]
        .run()

      // just like a regular http request we can access response status which is available via upgrade.response.status
      // status code 101 (Switching Protocols) indicates that server support WebSockets
      val connected = upgradeResponse.flatMap { upgrade =>
        if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
          Future.successful(Done)
        } else {
          throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
        }
      }

      // in a real application you would not side effect here
      connected.onComplete(println)
      closed.foreach(_ => println("closed"))
    }
  }

}
