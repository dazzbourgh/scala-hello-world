package models

import play.api.libs.json.{Format, Json}

object VkModels {

  case class Rule(value: String, tag: String) extends JsonLike

  case class RuleResponse(rule: Rule) extends JsonLike

  object Rule {
    implicit val format: Format[Rule] = Json.format
  }

  object RuleResponse {
    implicit val format: Format[RuleResponse] = Json.format
  }

}
