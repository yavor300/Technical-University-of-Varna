package inheritance

open class Base(val p: Int) {
  fun printInfo() {
    println("Value in Base: $p")
  }
}

class Derived(p: Int, val q: Int): Base(p) {

  fun printDerivedInfo() {
    printInfo()
    println("Additional value in Derived: $q")
  }
}

open class Shape() {

  open val vertexCount: Int = 0

  open fun draw() {
    println("Generic shape")
  }

  fun fill() {
    println("Filling the shape")
  }
}

class Circle: Shape() {
  override fun draw() {
    println("Circle")
  }
}

open class Triangle: Shape() {

  override val vertexCount: Int = 4

  final override fun draw() {
    println("Triangle")
  }
}

class NotOverrideFromTriangle(): Triangle() {
//  ERROR: 'Draw' is final and cannot be overriden
//  override fun draw() {
//    super.draw()
//  }

}

fun main() {
  val baseInstance = Base(10)
  baseInstance.printInfo()

  val derived: Derived = Derived(20, 30)
  derived.printDerivedInfo()

  println()
  val shapes = listOf(Shape(), Circle(), Triangle())
  for (shape in shapes) {
    shape.draw()
    shape.fill()
  }

}