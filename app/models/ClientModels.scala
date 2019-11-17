package models

import _root_.util.JsonLike
import play.api.libs.json._

object ClientModels {

  final case class Response(currency: String,
                            mentions: Long,
                            ratio: Double,
                            timestamp: Long) extends JsonLike

  final case class Request(from: Long, to: Long)


  object Request {
    /**
     * Mapping to read/write a Request out as a JSON value.
     */
    implicit val format: Format[Request] = Json.format
  }
}
