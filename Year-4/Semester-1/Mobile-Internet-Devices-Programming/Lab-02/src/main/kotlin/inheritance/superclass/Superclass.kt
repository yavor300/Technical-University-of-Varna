package inheritance.superclass

open class Rectangle {
  open fun draw() {
    println("Drawing a rectangle")
  }
  val borderColor: String get() = "black"
}

class FilledRectangle : Rectangle() {
  override fun draw() {
    super.draw()
    println("Filling the rectangle")
  }
  val fillColor: String get() = super.borderColor

  inner class Filler {
    fun fill() {
      println("Filling")
    }
    fun drawAndFill() {
      super@FilledRectangle.draw()
      fill()
      println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}")
    }
  }
}

fun main()
{
  val r: Rectangle = Rectangle()
  r.draw()
  r.borderColor.also { println(it) }
  val fr: FilledRectangle = FilledRectangle()
  fr.draw()
  fr.fillColor.also { println(it) }
  fr.borderColor.also { println(it) }
  fr.Filler().drawAndFill()
}