package pf.branches

import scala.reflect.ClassTag


case class TypedBranch [X: ClassTag, Y: ClassTag, O: ClassTag]
(f1: PartialFunction[X, O], f2: PartialFunction[Y, O]) {

  type ¬[A] = A => Option[Nothing]
  type ¬¬[A] = ¬[¬[A]]
  type ∨[T, U] = ¬[¬[T] with ¬[U]]


  type `Union[X, Y]`[Z] = ¬¬[Z] <:< ∨[X, Y]

  def apply[T](t: T)(implicit ev: `Union[X, Y]`[T]): O = t match {
    case x: X => f1(x)
    case y: Y => f2(y)
  }

  def apply() = new PartialFunction[Any, O] {
    override def isDefinedAt(x: Any): Boolean = x match {
      case x: X => f1.isDefinedAt(x)
      case y: Y => f2.isDefinedAt(y)
    }

    override def apply(x: Any) = x match {
      case x: X => f1(x)
      case y: Y => f2(y)
    }
  }

}
