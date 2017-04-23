package pf.objects.classic

class Echo {
  private var volume: Int = 5
  private var playing: Option[String] = None

  //volume logic
  def volumeUp = volume += 1
  def volumeDown = volume -= 1
  def setVolume(v: Int) = volume = v
  def getVolume: String = s"The current volume is $volume"

  //music logic
  def play(piece: String) = {
    playing = Some(piece)
  }
  def stop = playing = None

  //temperature logic
  def getTemperature(place: String): Option[String] = {
    val temp = 70 //call service
    Some(s"The current temperature in $place is $temp degrees")
  }
}
