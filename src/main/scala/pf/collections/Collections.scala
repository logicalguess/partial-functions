package pf.collections

import pf.basics.Definition._

object Collections {

  val l: List[Any] = "hello" :: steve :: "world" :: dan :: Nil

  def filterStrings(list: List[Any]): List[Any] = list filter {
    _.isInstanceOf[String]
  }

  def filterPeople(list: List[Any]): List[Any] = list filter {
    _.isInstanceOf[Person]
  }

  def collectStrings(list: List[Any]): List[String] = list collect {
    case s: String => s
  }

  def collectPeople(list: List[Any]): List[Person] = list collect {
    case p: Person => p
  }

  def flatMap(list: List[Any]): List[String] = list flatMap {
    case st: String => List(st)
    case _ => Nil
  }
}
