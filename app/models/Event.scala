package models

import play.api.libs.json.{Format, Json}
import util.JsonLike

case class Event(event_url: String, text: String, action: String, tags: Array[String]) extends JsonLike

object Event {
  implicit val eventFormat: Format[Event] = Json.format
}
