package util

import com.google.gson.Gson
import util.JsonLike.gson

abstract class JsonLike {
  override def toString: String = gson toJson this
}

object JsonLike {
  private val gson = new Gson()
}
