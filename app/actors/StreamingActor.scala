package actors

import akka.actor.Actor
import models.Event
import play.api.Logger

// TODO: implement receiving of event
class StreamingActor extends Actor {
  private val log = Logger(getClass)

  override def receive: Receive = {
    case e: Event => log.info(s"Received event: $e")
  }
}
