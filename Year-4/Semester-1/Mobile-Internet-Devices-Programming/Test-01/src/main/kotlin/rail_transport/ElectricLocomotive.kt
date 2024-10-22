package rail_transport

class ElectricLocomotive : Locomotive() {

  override fun getType(): LocomotiveType {
    return LocomotiveType.ELECTRIC
  }

  override fun calculate(): Double {
    return 7.0
  }
}
