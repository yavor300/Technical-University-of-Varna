package rail_transport

class SteamLocomotive : Locomotive() {

  override fun getType(): LocomotiveType {
    return LocomotiveType.STEAM
  }

  override fun calculate(): Double {
    return 9.6
  }
}
