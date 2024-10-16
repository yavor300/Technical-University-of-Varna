package inheritance.withinterface

interface Shape {

  val vertexCount: Int
}

class Rectangle(override val vertexCount: Int = 4) : Shape

class Polygon : Shape {
  override val vertexCount: Int = 0
}

fun main() {
  val r = Rectangle(5)
  r.vertexCount.also(::println)
  val p = Polygon()
  p.vertexCount.also(::println)
}