package pf.basics

import pf.basics.Definition._
import pf.basics.Operators._

import org.scalatest.{Matchers, WordSpecLike}

class OperatorsTest extends WordSpecLike with Matchers {

  "Partial Function Operators" should {
    "andThen" in {
      earlyRiser(dan) shouldEqual true
      earlyRiser(chuck) shouldEqual false

      a [MatchError] should be thrownBy earlyRiser(steve)
    }

    "andThen Option" in {
      lateRiserOption(dan) shouldEqual false
      lateRiserOption(chuck) shouldEqual true
      lateRiserOption(steve) shouldEqual false
    }

    "applyOrElse" in {
      earlyRiser.applyOrElse(dan, handleUndefined) shouldEqual true
      earlyRiser.applyOrElse(chuck, handleUndefined) shouldEqual false
      earlyRiser.applyOrElse(steve, handleUndefined) shouldEqual true
    }
    
    "orElse" in {
      wakeUpTimeUnion.isDefinedAt(dan) shouldEqual true
      wakeUpTimeUnion.isDefinedAt(chuck) shouldEqual true
      wakeUpTimeUnion.isDefinedAt(steve) shouldEqual false

      a [MatchError] should be thrownBy wakeUpTimeUnion(steve)

      wakeUpTimeUnion.isDefinedAt(jane) shouldEqual true
      wakeUpTimeUnion.isDefinedAt(mike) shouldEqual true
      wakeUpTimeUnion.isDefinedAt(kate) shouldEqual false
      wakeUpTimeUnion.isDefinedAt(john) shouldEqual true


      a [MatchError] should be thrownBy wakeUpTimeUnion(kate)
    }
  }

}
