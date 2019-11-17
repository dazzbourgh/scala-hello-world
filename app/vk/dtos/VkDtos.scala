package vk.dtos

import models.Event
import play.api.libs.json.{Format, Json, Reads}
import util.JsonLike
import vk.dtos.VkModels.Rule

object VkDtos {

  case class RuleDto(rule: Rule) extends JsonLike

  case class RulesDto(rules: Array[Rule]) extends JsonLike

  case class TagDto(tag: String) extends JsonLike

  case class StreamingDto(endpoint: String, key: String) extends JsonLike

  case class Response[T](response: T) extends JsonLike

  case class RuleCodeResponseDto(code: Int, rules: Option[Array[Rule]], error: Option[Message])

  case class Message(message: String) extends JsonLike

  case class EventCodeResponseDto(code: Int, event: Event) extends JsonLike


  object EventCodeResponseDto {
    implicit val eventDtoFormat: Format[EventCodeResponseDto] = Json.format
  }

  object StreamingDto {
    implicit val streamingResponse: Format[StreamingDto] = Json.format
  }

  object RuleDto {
    implicit val rule: Format[RuleDto] = Json.format
  }

  object RulesDto {
    implicit val reads: Format[RulesDto] = Json.format
  }

  object RuleCodeResponseDto {
    implicit val reads: Format[RuleCodeResponseDto] = Json.format
  }

  object Message {
    implicit val reads: Format[Message] = Json.format
  }

  object TagDto {
    implicit val reads: Format[TagDto] = Json.format
  }

  object Response {
    implicit def reads[T](implicit underlying: Reads[T]): Reads[Response[T]] = {
      Reads[Response[T]] {
        json =>
          (json \ "response").validate(underlying).map {
            Response(_)
          }
      }
    }
  }

}

object VkModels {

  case class Rule(value: String, tag: String) extends JsonLike

  object Rule {
    implicit val rule: Format[Rule] = Json.format
  }

}
