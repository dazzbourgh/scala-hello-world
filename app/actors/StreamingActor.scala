package actors

import akka.actor.Actor
import play.api.Logger
import vk.dtos.VkModels.Event

// TODO: implement receiving of event
class StreamingActor extends Actor {
  private val log = Logger(getClass)

  override def receive: Receive = {
    case e: Event => log.info(s"Received event: $e")
  }
}
