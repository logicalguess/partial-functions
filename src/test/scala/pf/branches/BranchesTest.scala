package pf.branches

import pf.branches.Branches._

import org.scalatest.{Matchers, WordSpecLike}

class BranchesTest extends WordSpecLike with Matchers {

  "Partial Function merging" should {
    "branch" in {
      val branch = Branch(int, string)

      branch(77) shouldBe 77
      branch("xyz") shouldBe 3
      a [MatchError] should be thrownBy branch(true)
    }

    "type safe branch" in {
      val branch = TypedBranch(int, string)

      branch(77) shouldBe 77
      branch("xyz") shouldBe 3
      //branch(true) //compile time error
    }
  }

}
