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










//val flow: Flow[Event, State, _] = Flow[Event].scan(State())((state: State, event: Event) => logic(event, state))
//val flow: Flow[Event, State, _] = logic


//  val s: Source[State, _] = Source.fromGraph(GraphDSL.create() { implicit builder =>
//    import GraphDSL.Implicits._
//    val s = builder.add(source)
//    val f = builder.add(logic)
//    s.out ~> f.in
//    SourceShape(f.out)
//  })
//
//  val g: RunnableGraph[NotUsed] = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
//    import GraphDSL.Implicits._
//    source ~> logic ~> sink
//    ClosedShape
//  })

//val computation = s.to(sink).run()
//val computation: SourceQueueWithComplete[Event] = g.run()

