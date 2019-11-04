package models

import com.google.gson.Gson
import models.JsonLike.gson

abstract class JsonLike {
  override def toString: String = gson toJson this
}

object JsonLike {
  private val gson = new Gson()
}
