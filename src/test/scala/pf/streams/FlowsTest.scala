package pf.streams

import java.time.LocalTime

import pf.basics.Definition._
import pf.streams.Flows._

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Keep
import akka.stream.testkit.scaladsl.{TestSink, TestSource}
import org.scalatest.{Matchers, WordSpecLike}

class FlowsTest extends WordSpecLike with Matchers {
  implicit val system = ActorSystem("akka-stream")
  implicit val mat = ActorMaterializer()

  "Partial Function on Streams" should {

    "list source" in {

      listSource.collect(wakeUpTime).runWith(TestSink.probe[LocalTime]).request(3)
        .expectNextUnorderedN(List(LocalTime.of(6, 42), LocalTime.of(7, 25)))
    }

    "queue source" in {

     val (computation, probe) = queueSource.collect(wakeUpTime).toMat(TestSink.probe[LocalTime])(Keep.both).run()

      computation.offer(steve)
      computation.offer(dan)
      computation.offer(chuck)

      probe.request(3)
        .expectNextUnorderedN(List(LocalTime.of(6, 42), LocalTime.of(7, 25)))
    }

    "flow" in {

      val (pub, sub) = TestSource.probe[Person]
        .via(flow)
        .toMat(TestSink.probe[Any])(Keep.both)
        .run()

      sub.request(2)
      pub.sendNext(steve)
      pub.sendNext(dan)
      sub.expectNext(LocalTime.of(6, 42))

      sub.request(1)
      pub.sendNext(chuck)
      sub.expectNext(LocalTime.of(7, 25))

    }
  }
}
