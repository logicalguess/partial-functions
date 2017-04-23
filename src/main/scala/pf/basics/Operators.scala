package pf.basics

import java.time.LocalTime

import Definition._

object Operators {

  val isEarly: PartialFunction[LocalTime, Boolean] = {
    case t if t.isBefore(LocalTime.of(7, 0)) => true
    case _ => false
  }

  val earlyRiser = wakeUpTime andThen isEarly

  //applyOrElse
  val handleUndefined: PartialFunction[Person, Boolean] = {
    case _ => true
  }

  //option
  val isLateOption: PartialFunction[Option[LocalTime], Boolean] = {
    case Some(t) if t.isBefore(LocalTime.of(7, 0)) => false
    case None => false
    case _ => true
  }

  val lateRiserOption = wakeUpTime.lift andThen isLateOption

  //---
  val jane = Person("Jane")
  val mike = Person("Mike")
  val kate = Person("Kate")
  val john = Person("John")

  val wakeUpTimeCaseSecond: PartialFunction[Person, LocalTime] = {
    case `jane` => LocalTime.of(6, 12)
    case `mike` => LocalTime.of(5, 3)
    case `john` => LocalTime.of(10, 47)
  }

  //orElse
  val wakeUpTimeUnion = wakeUpTimeCase orElse wakeUpTimeCaseSecond

}
