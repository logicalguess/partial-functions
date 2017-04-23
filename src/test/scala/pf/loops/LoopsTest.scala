package pf.loops

import pf.loops.Loops._

import org.scalatest.{Matchers, WordSpecLike}

class LoopsTest extends WordSpecLike with Matchers {

  "PartialFunction as iterator" should {
    "fibonacci" in {
      //produce Fibonacci numbers and stop when above 15
      Iterator(restrict andThen fibs)((1, 1)) should be (21, 13)
    }
  }

}
