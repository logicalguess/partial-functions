package pf.objects.component

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.scalatest.{Matchers, WordSpecLike}
import pf.objects.domain.Behavior._
import pf.objects.domain._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn

class ComponentTest extends WordSpecLike with Matchers {

  "PartialFunction as interactor" should {
    "component" in {

      implicit val system = ActorSystem("akka-stream-partial-functions")
      implicit val materializer = ActorMaterializer()

      val interactor = Interactor[Event, State, State](logic, maxVolume)
      val component = LogicComponent(State(), interactor, Sink.foreach(println))

      List(VolumeDown,
        GetVolume,
        Play("Taylor Swift"),
        GetTemperature("New York City, NY"),
        Stop)
        .foreach(component.receive)

      val whenTerminated = system.terminate()
      Await.result(whenTerminated, Duration.Inf)

    }

    "wait" in {
      StdIn.readLine()
    }
  }

}
