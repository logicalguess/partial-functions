package pf.loops

case class Iterator[A](f: PartialFunction[A, A]) {
  private def apply(): PartialFunction[A, A]  = {
    case a if f.isDefinedAt(a) =>
      var state = a
      while (f.isDefinedAt(state))
        state = f(state)
      state
  }

  def apply(a: A): A = apply()(a)
}

