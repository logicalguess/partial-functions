package pf.objects.stream

import akka.stream.scaladsl.{Sink, Source, SourceQueueWithComplete}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import pf.objects.domain.Behavior._
import pf.objects.domain._

case class EchoScan(sink: Sink[State, _])(implicit mat: ActorMaterializer) {

  val source: Source[Event, SourceQueueWithComplete[Event]] =
    Source.queue[Event](10, OverflowStrategy.backpressure)

  val computation: SourceQueueWithComplete[Event] = source.via(logic) /*.via(maxVolume)*/ .to(sink).run()

  def receive(event: Event): Unit = {
    computation.offer(event)
  }
}