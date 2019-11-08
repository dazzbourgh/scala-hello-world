package vk.dtos

import models.JsonLike
import play.api.libs.json.{Format, Json}
import vk.dtos.VkModels.Rule

object VkDtos {

  case class RuleDto(rule: Rule) extends JsonLike

  case class RulesDto(rules: Array[Rule]) extends JsonLike

  case class StreamingDto(endpoint: String, key: String) extends JsonLike

  case class Response[T](response: T)

  object StreamingDto {
    implicit val streamingResponse: Format[StreamingDto] = Json.format
  }

  object RuleDto {
    implicit val rule: Format[RuleDto] = Json.format
  }

  object RulesDto {
    implicit val reads: Format[RulesDto] = Json.format
  }

}

object VkModels {

  case class Rule(value: String, tag: String) extends JsonLike

  object Rule {
    implicit val rule: Format[Rule] = Json.format
  }

}
