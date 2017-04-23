package pf.collections

import org.scalatest.{Matchers, WordSpecLike}
import pf.basics.Definition.Person
import pf.collections.Collections._

class CollectionsTest extends WordSpecLike with Matchers {

  "Partial Function on Collections" should {
    "filter" in {
      //val ls: List[String] = filterStrings(l)
      filterStrings(l) shouldBe a [List[_]] //List[Any]

      //val lp: List[Person] = filterPeople(l)
      filterPeople(l) shouldBe a [List[_]] //List[Any]
    }

    "collect" in {
      val ls: List[String] = collectStrings(l)
      collectStrings(l) shouldBe a [List[_]] //List[String]

      val lp: List[Person] = collectPeople(l)
      collectPeople(l) shouldBe a [List[_]] //List[String]
    }
  }
}
