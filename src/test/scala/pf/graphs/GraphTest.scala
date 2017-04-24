package pf.graphs

import pf.graphs.Graphs._
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Keep, Sink, Source, SourceQueueWithComplete}
import akka.stream.testkit.scaladsl.TestSink
import org.scalatest.{Matchers, WordSpecLike}
import pf.streams.FlowComponent

class GraphTest extends WordSpecLike with Matchers {
  implicit val system = ActorSystem("partial-functions")
  implicit val mat = ActorMaterializer()

  "PartialFunctions as nodes in graphs" should {

    "composition" in {
      diamondComposition(7) shouldBe "7!7#"
    }

    "flow" in {
      val queueSource: Source[Int, SourceQueueWithComplete[Int]] =
        Source.queue[Int](10, OverflowStrategy.backpressure)

      val (computation, probe) = queueSource.via(diamondFlow).toMat(TestSink.probe[String])(Keep.both).run()

      computation.offer(7)

      probe.request(1)
        .expectNextUnorderedN(List("7!7#"))
    }

    "component" in {
      val diamond = FlowComponent(diamondFlow, Sink.foreach(println))
      diamond.receive(7)
    }
  }

}
