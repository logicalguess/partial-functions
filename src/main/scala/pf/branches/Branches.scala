package pf.branches

object Branches {

  val int: PartialFunction[Int, Int] = {
    case i: Int if i >= 5 => i
  }

  val string: PartialFunction[String, Int] = {
    case s: String => s.length
  }

  val bool: PartialFunction[Boolean, Int] = {
    case true => 1;
    case false => 0
  }

  val nothing: PartialFunction[Any, Nothing] = {
    case _ => throw new RuntimeException
  }
}
