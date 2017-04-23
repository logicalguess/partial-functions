package pf.loops

object Loops {

  val fibs: PartialFunction[(Int, Int), (Int, Int)] = {
    case (f1: Int, f2: Int) if (f1 >= 0) => (f1 + f2, f1)
  }

  val restrict: PartialFunction[(Int, Int), (Int, Int)] = {
    case (f1: Int, f2: Int) if f1 < 15 => (f1, f2)
  }

}
