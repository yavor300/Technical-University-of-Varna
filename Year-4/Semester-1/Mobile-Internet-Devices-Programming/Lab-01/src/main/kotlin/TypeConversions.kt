fun main(args: Array<String>) {

  val i: Int = 6

  val b: Byte = i.toByte()
  println("Converted Int to Byte: $b")

  val c: Double = b.toDouble()
  println("Converted Byte to Double: $c")

  val d: String = b.toString()
  println("Converted Byte to String: $d")

  val e = b.toInt()
  println("Converted Byte back to Int: $e")
}
