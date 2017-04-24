package pf.graphs

import akka.stream.FlowShape
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Zip}

object Graphs {

  val convert: PartialFunction[Int, String] = {
    case i: Int if i > 0 => i.toString
  }
  val bang: Function[String, String] = { s: String => s + "!" }
  val hash: Function[String, String] = { s: String => s + "#" }
  val concatenate: Function[(String, String), String] = { s => s._1 + s._2 }

  val diamondComposition: Int => String = i => {
    val s = convert(i)
    val b = bang(s)
    val h = hash(s)
    concatenate(b, h)
  }

  implicit def FunctionToFlow[I, O](f: Function[I, O]): Flow[I, O, _] =
    Flow[I].map(f)

  val diamondFlow: Flow[Int, String, _] = Flow.fromGraph(GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._

    val input = builder.add(Flow[Int])
    val bcast = builder.add(Broadcast[String](2))
    val zip = builder.add(Zip[String, String])
    val output = builder.add(Flow[String])

    input.out ~> convert ~> bcast ~> bang ~> zip.in0
    bcast ~> hash ~> zip.in1
    zip.out ~> concatenate ~> output

    FlowShape(input.in, output.out)
  })
}
