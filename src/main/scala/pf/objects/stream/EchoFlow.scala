package pf.objects.stream

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Flow, GraphDSL, Sink, Source, SourceQueueWithComplete}
import akka.{Done, NotUsed}
import pf.objects.domain.Behavior._
import pf.objects.domain._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.StdIn

/**
  * Created by logicalguess on 4/16/17.
  */
object EchoFlow {
  private var state: State = State()

  def receive(event: Event): Unit = {
    computation.offer(event)
  }

  val flow1: Flow[Event, Event, _] = Flow[Event].filter(logic.isDefinedAt(_, state))
  val flow2: Flow[Event, State, _] = Flow[Event].map(e => {
    state = logic(e, state)
    state
  })

//  val g: RunnableGraph[NotUsed] = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
//    import GraphDSL.Implicits._
//    source ~> flow1 ~> flow2 ~> sink
//    ClosedShape
//  })

//  val g: Sink[Event, NotUsed] = Sink.fromGraph(GraphDSL.create() { implicit builder =>
//    import GraphDSL.Implicits._
//    val f1 = builder.add(flow1)
//    f1 ~> flow2 ~> sink
//    SinkShape(f1.in)
//  })

  val g: Flow[Event, State, NotUsed] = Flow.fromGraph(GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._
    val f1 = builder.add(flow1)
    val f2 = builder.add(flow2)

    f1 ~> f2
    FlowShape(f1.in, f2.out)
  })


  val source: Source[Event, SourceQueueWithComplete[Event]] =
    Source.queue[Event](10, OverflowStrategy.backpressure)

  val sink: Sink[State, Future[Done]] = Sink.foreach(println)

  implicit val system = ActorSystem("akka-stream-partial-functions")
  implicit val materializer = ActorMaterializer()
  val computation: SourceQueueWithComplete[Event] = source.via(g).to(sink).run()

  //function
//  val logic: PartialFunction[(Event, State), State] = {
//    case (VolumeUp, s) => s.copy(s.volume + 1) //validate
//    case (VolumeDown, s) => s.copy(s.volume - 1) //validate
//    case (SetVolume(l), s) => s.copy(l) //validate
//
//    case (GetVolume, s) => s.copy(s.volume, s.playing, Some(s"The current volume is ${s.volume}")) //validate
//
//    case (Play(p), s) => s.copy(s.volume, Some(p))
//    case (Stop, s) => if (s.playing.isDefined) s.copy(s.volume, None) else s
//
//    case (GetTemperature(place), s) => {
//      val temp = 70 //call service
//      s.copy(s.volume, s.playing, Some(s"The current temperature in $place is $temp degrees"))
//    }
//  }

  def main(args: Array[String]): Unit = {

    List(VolumeDown,
      GetVolume,
      Play("Taylor Swift"),
      GetTemperature("New York City, NY"),
      Stop)
      .foreach(EchoFlow.receive)

    StdIn.readLine()

    val whenTerminated = system.terminate()
    Await.result(whenTerminated, Duration.Inf)

  }


}
