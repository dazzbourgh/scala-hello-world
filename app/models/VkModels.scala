package models

import com.google.gson.Gson
import models.JsonLike.gson

abstract class JsonLike {
  override def toString: String = gson toJson this
}

object JsonLike {
  private val gson = new Gson()
}

case class Rule(val value: String, val tag: String) extends JsonLike

case class RuleBody(val rule: Rule) extends JsonLike
