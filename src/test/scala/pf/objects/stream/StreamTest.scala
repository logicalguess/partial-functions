package pf.objects.stream

import pf.objects.domain._
import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.scalatest.{Matchers, WordSpecLike}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.StdIn

class StreamTest extends WordSpecLike with Matchers {

  "PartialFunction as node in a computation graph" should {
    "flow" in {

      implicit val system = ActorSystem("akka-stream-partial-functions")
      implicit val materializer = ActorMaterializer()

      val sink: Sink[State, Future[Done]] = Sink.foreach(println)


      List(VolumeDown,
        GetVolume,
        Play("Taylor Swift"),
        GetTemperature("New York City, NY"),
        Stop)
        .foreach(EchoScan(sink).receive)

      StdIn.readLine()

      val whenTerminated = system.terminate()
      Await.result(whenTerminated, Duration.Inf)
    }
  }

}
