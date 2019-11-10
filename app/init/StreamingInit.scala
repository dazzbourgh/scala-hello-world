package init

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import vk.methods.VkMethods

@Singleton
class StreamingInit @Inject()(private val system: ActorSystem,
                              private val apiBaseUrl: String,
                              private val accessToken: String,
                              private val v: String) {
  val log = play.api.Logger(getClass)
  VkMethods.apiBaseUrl = apiBaseUrl
  VkMethods.accessToken = accessToken
  VkMethods.v = v
}

object Test {
  def main(args: Array[String]): Unit = {
    VkMethods.Streaming.openConnection
  }
}
