package pf.basics

import java.time.LocalTime

import org.scalatest.{Matchers, WordSpecLike}
import Definition._

class DefinitionTest extends WordSpecLike with Matchers {

  "Partial Function Definition" should {
    "constructor" in {
      wakeUpTime.isDefinedAt(dan) shouldEqual true
      wakeUpTime.isDefinedAt(chuck) shouldEqual true
      wakeUpTime.isDefinedAt(steve) shouldEqual false

      a [MatchError] should be thrownBy wakeUpTime(steve)
    }

    "case" in {
      wakeUpTimeCase.isDefinedAt(dan) shouldEqual true
      wakeUpTimeCase.isDefinedAt(chuck) shouldEqual true
      wakeUpTimeCase.isDefinedAt(steve) shouldEqual false

      a [MatchError] should be thrownBy wakeUpTimeCase(steve)
    }

    "map" in {
      val fromMap: PartialFunction[Person, LocalTime] = wakeUpTimeMap

      wakeUpTimeCase.isDefinedAt(dan) shouldEqual true
      wakeUpTimeCase.isDefinedAt(chuck) shouldEqual true
      wakeUpTimeCase.isDefinedAt(steve) shouldEqual false

      a [NoSuchElementException] should be thrownBy fromMap(steve)
    }

    "predicate" in {

      nameLength.isDefinedAt(dan) shouldEqual true
      nameLength.isDefinedAt(chuck) shouldEqual true
      nameLength.isDefinedAt(steve) shouldEqual true

      import Length._
      nameLength(dan) shouldEqual VeryShort
      nameLength(chuck) shouldEqual Short
      nameLength(steve) shouldEqual Short
    }
  }

}
