package rail_transport

class PassengerWagon : Wagon() {

  override fun getType(): WagonType {
    return WagonType.PASSENGER
  }
}
