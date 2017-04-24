package pf.objects.domain

import akka.stream.scaladsl.Flow

object Behavior {

  //function
  val logic: PartialFunction[(Event, State), State] = {
    case (VolumeUp, s) => s.copy(s.volume + 1) //validate
    case (VolumeDown, s) => s.copy(s.volume - 1) //validate
    case (SetVolume(l), s) => s.copy(l) //validate

    case (GetVolume, s) => s.copy(s.volume, s.playing,
      Some(s"The current volume is ${s.volume}")) //validate

    case (Play(p), s) => s.copy(s.volume, Some(p))
    case (Stop, s) =>
      if (s.playing.isDefined) s.copy(s.volume, None) else s

    case (GetTemperature(place), s) => {
      val temp = 70 //call service
      s.copy(s.volume, s.playing,
        Some(s"The current temperature in $place is $temp degrees"))
    }
  }

  //----------

  val maxVolume: PartialFunction[State, State] = {
    case s => s.copy(volume = 10)
  }

  //-----------
  implicit val init: State = State()

  implicit def partialFunctionToFlow[E, S](f: PartialFunction[(E, S), S])(implicit init: S): Flow[E, S, _] =
    Flow[E].scan(init)((state: S, event: E) => f(event, state))

  implicit def partialFunctionToFlow[I, O](f: PartialFunction[I, O]): Flow[I, O, _] =
    Flow[I].map(f)

}
