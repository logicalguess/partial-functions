package pf.branches

import scala.reflect.ClassTag

case class Branch[X: ClassTag, Y: ClassTag, O](f1: PartialFunction[X, O], f2: PartialFunction[Y, O])
  extends PartialFunction[Any, O] {
  override def isDefinedAt(x: Any): Boolean = x match {
    case x: X => f1.isDefinedAt(x)
    case y: Y => f2.isDefinedAt(y)
  }

  override def apply(x: Any) = x match {
    case x: X => f1(x)
    case y: Y => f2(y)
  }
}
