package pf.streams

import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Sink, Source, SourceQueueWithComplete}


case class FlowComponent[I, O, S](flow: Flow[I, O, _], sink: Sink[O, _])
                                 (implicit mat: ActorMaterializer) {

  private lazy val source: Source[I, SourceQueueWithComplete[I]] =
    Source.queue[I](10, OverflowStrategy.backpressure)

  private lazy val computation: SourceQueueWithComplete[I] = source.via(flow).to(sink).run()

  def receive(event: I): Unit = {
    computation.offer(event)
  }
}

