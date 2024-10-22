package rail_transport

class Station(private val name: String, private val town: Town, val coordinate: Coordinate) {

  override fun toString(): String {
    return "Station(name='$name', town=$town, coordinate=$coordinate)"
  }
}
