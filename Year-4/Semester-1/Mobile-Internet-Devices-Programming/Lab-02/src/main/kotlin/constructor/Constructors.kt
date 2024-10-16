@file:Suppress("ConvertSecondaryConstructorToPrimary")

package constructor

class Person constructor(firstName: String) {}

class PersonWithoutConstructorKeyword(firstName: String, age: Int) {}

class InitOrderDemo(name: String) {

  val firstProperty = "First property: $name".also(::println)

  init {
    println("First initializer block that prints $name")
  }

  val secondProperty = "Second property: ${name.length}".also(::println)

  init {
    println("Second initializer block that prints ${name.length}")
  }
}

class Customer(name: String) {

  val customerKey = name.uppercase()
}

class PersonWithDefaultParameter(
  val firstName: String,
  val lastName: String,
  var isEmployed: Boolean = true, // trailing comma
) {}

annotation class Inject

class CustomerWithAnnotationAndModifier public @Inject constructor(name: String)

class Pet {

  constructor(owner: PersonWithPets) {
    owner.pets.add(this)
  }
}

class PersonWithPets(val pets: MutableList<Pet> = mutableListOf())

class PersonWithConstructorDelegation(val name: String) {
  private val children: MutableList<PersonWithConstructorDelegation> = mutableListOf()

  constructor(name: String, parent: PersonWithConstructorDelegation) : this(name) {
    parent.children.add(this)
  }
}

class DemoConstructor() {
  init {
    println("Init block")
  }

  constructor(i: Int) : this() {
    println("Constructor $i")
  }
}

class DontCreateMe private constructor() {}

fun main(args: Array<String>) {

  val initDemo: InitOrderDemo = InitOrderDemo("test")

  var constructor: DemoConstructor = DemoConstructor()
  constructor = DemoConstructor(1)

}