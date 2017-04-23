package pf.objects.actor

import akka.actor.{ActorSystem, Props}
import org.scalatest.{Matchers, WordSpecLike}
import pf.objects.domain._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ActorTest extends WordSpecLike with Matchers {

  "PartialFunction as actor" should {
    "actor" in {

      implicit val system = ActorSystem("partial-functions")
      val echoActor = system.actorOf(Props(new EchoActor))

      echoActor ! VolumeDown
      echoActor ! GetVolume
      echoActor ! Play("Taylor Swift")
      echoActor ! GetTemperature("New York City, NY")
      echoActor ! Stop

      val whenTerminated = system.terminate()
      Await.result(whenTerminated, Duration.Inf)
    }
  }

}
