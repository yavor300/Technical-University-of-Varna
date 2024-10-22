package rail_transport

class FreightWagon : Wagon() {

  override fun getType(): WagonType {
    return WagonType.FREIGHT
  }
}
