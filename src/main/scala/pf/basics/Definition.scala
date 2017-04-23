package pf.basics

import java.time.LocalTime

object Definition {
  case class Person(name: String)

  val steve = Person("Steve")
  val dan = Person("Dan")
  val chuck = Person("Chuck")

  //constructor
  val wakeUpTime : PartialFunction[Person, LocalTime] = new PartialFunction[Person, LocalTime] {
    override def isDefinedAt(p: Person): Boolean =
      p == dan || p ==chuck

    override def apply(p: Person): LocalTime = p match {
      case `dan` => LocalTime.of(6, 42)
      case `chuck` => LocalTime.of(7, 25)
    }
  }

  //case
  val wakeUpTimeCase: PartialFunction[Person, LocalTime] = {
    case `dan` => LocalTime.of(6, 42)
    case `chuck` => LocalTime.of(7, 25)
  }

  //map
  val wakeUpTimeMap: Map[Person, LocalTime] =
    Map(dan -> LocalTime.of(6, 42), chuck -> LocalTime.of(7, 25))

  implicit def mapToPartialFunction[K, V](m: Map[K, V]) = new PartialFunction[K, V] {
    override def isDefinedAt(k: K): Boolean = m.contains(k)
    override def apply(k: K): V = m(k)
  }

  //predicate
  object Length extends Enumeration {
    type Length = Value
    val VeryShort, Short, Long, VeryLong = Value
  }
  import Length._

  val nameLength: PartialFunction[Person, Length] = {
    case p if p.name.length <= 3 => VeryShort
    case p if p.name.length <= 5 => Short
    case p if p.name.length <= 8 => Long
    case _ => VeryLong
  }
}
