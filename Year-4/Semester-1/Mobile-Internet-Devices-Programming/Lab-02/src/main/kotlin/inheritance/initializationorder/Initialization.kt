package inheritance.initializationorder

open class Base(val name: String) {

  init {
    println("Initializing a base class")
  }

  open val size: Int = name.length.also { println("Initializing size in the base class: $it") }
}

class Derived(
  name: String,
  val lastName: String,
) : Base(name.replaceFirstChar { it.uppercase() }.also { println("Argument for the base class: $it") }) {
  init {
    println("Initializing a derived class")
  }

  override val size: Int =
    (super.size + lastName.length).also { println("Initializing size in the derived class: $it") }
}

fun main() {
  val base: Base = Base("yavor")
  val derived: Derived = Derived("yavor", "chamov")
}