package pf.objects.domain

// events
sealed trait Event

object VolumeUp extends Event
object VolumeDown extends Event
case class SetVolume(level: Int) extends Event
object GetVolume extends Event

case class Play(piece: String) extends Event
object Stop extends Event

case class GetTemperature(place: String) extends Event

//state
case class State(volume: Int = 5,
                 playing: Option[String] = None,
                 response: Option[String] = None)

