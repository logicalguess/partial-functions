package pf.objects.actor

import pf.objects.domain.Behavior._
import pf.objects.domain._

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging


class EchoActor extends Actor with LazyLogging {
  private var state: State = State()

  override def receive: Receive = {
    case msg: Event => {
      state = logic(msg, state)
      println(s"Message in EchoActor: $msg. New state: $state")
      sender() ! state
    }
  }
}
