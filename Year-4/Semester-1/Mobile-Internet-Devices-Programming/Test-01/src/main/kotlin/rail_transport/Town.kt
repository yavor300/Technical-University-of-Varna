package rail_transport

class Town(private val name: String, private val postalCode: String) {

  override fun toString(): String {
    return "Town(name='$name', postalCode='$postalCode')"
  }
}
