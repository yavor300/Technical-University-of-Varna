package rail_transport

class DieselLocomotive : Locomotive() {

  override fun getType(): LocomotiveType {
    return LocomotiveType.DIESEL
  }

  override fun calculate(): Double {
    return 8.5
  }
}
