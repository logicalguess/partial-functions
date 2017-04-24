package pf.objects.classic

class Echo {
  private var volume: Int = 5
  private var playing: Option[String] = None

  //volume logic
  def volumeUp(): Unit = volume += 1
  def volumeDown(): Unit = volume -= 1
  def setVolume(v: Int): Unit = volume = v
  def getVolume: String = s"The current volume is $volume"

  //music logic
  def play(piece: String): Unit = {
    playing = Some(piece)
  }
  def stop(): Unit = playing = None

  //temperature logic
  def getTemperature(place: String): Option[String] = {
    val temp = 70 //call service
    Some(s"The current temperature in $place is $temp degrees")
  }
}
