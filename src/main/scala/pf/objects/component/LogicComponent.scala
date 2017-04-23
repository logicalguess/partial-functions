package pf.objects.component

import akka.stream.scaladsl.{Flow, Sink, Source, SourceQueueWithComplete}
import akka.stream.{ActorMaterializer, OverflowStrategy}


case class Interactor[I, O, S](logic: PartialFunction[(I, S), S], transformer: Function[S, O])
  (implicit mat: ActorMaterializer) {

  def apply(implicit init: S): Flow[I, O, _] = {
    val f = Flow[I].scan(init)((state: S, event: I) => logic(event, state))
    val t = Flow[S].map(transformer)
    f.via(t)
  }
}

case class LogicComponent[I, O, S](init: S, interactor: Interactor[I, O, S], sink: Sink[O, _])
  (implicit mat: ActorMaterializer) {

  private lazy val source: Source[I, SourceQueueWithComplete[I]] =
    Source.queue[I](10, OverflowStrategy.backpressure)

  implicit def interactorToFlow[I, O](i: Interactor[I, O, S]): Flow[I, O, _] = i(init)

  private lazy val computation: SourceQueueWithComplete[I] = source.via(interactor).to(sink).run()

  def receive(event: I): Unit = {
    computation.offer(event)
  }
}


//val flow: Flow[Ivent, State, _] = Flow[Ivent].scan(State())((state: State, event: Event) => logic(event, state))
//val flow: Flow[Ivent, State, _] = logic


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
//val computation: SourceQueueWithComplete[Ivent] = g.run()

