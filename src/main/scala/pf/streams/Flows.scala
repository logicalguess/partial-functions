package pf.streams

import java.time.LocalTime

import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Source, SourceQueueWithComplete}
import pf.basics.Definition._

object Flows {

  val listSource = Source(List(steve, chuck, dan))

  val queueSource: Source[Person, SourceQueueWithComplete[Person]] =
    Source.queue[Person](10, OverflowStrategy.backpressure)

  val flow: Flow[Person, LocalTime, _] = Flow[Person].collect(wakeUpTime)

}
