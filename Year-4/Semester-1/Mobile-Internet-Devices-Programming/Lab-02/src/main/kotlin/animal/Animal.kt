package animal

interface Mammal {

  fun isMammal(): Boolean
}

abstract class Animal(val age: Int, val gender: String) : Mammal {

  fun mate() {
    println("Mating")
  }
}

class Duck(age: Int, gender: String, val beakColor: String = "Yellow"): Animal(age, gender) {

  override fun isMammal(): Boolean = false

  fun swim() {
    println("Duck is swimming")
  }

  fun quack() {
    println("Duck is quacking")
  }
}

class Fish(age: Int, gender: String, val canEat: Boolean) : Animal(age, gender) {

  override fun isMammal(): Boolean = false

  fun swim() {
    println("Fish is swimming")
  }
}

class Zebra(age: Int, gender: String, val isWild: Boolean) : Animal(age, gender) {

  override fun isMammal(): Boolean = true

  fun run() {
    println("Zebra is running")
  }
}

fun main() {
  val ducky = Duck(2, "Female")
  ducky.swim()
  ducky.quack()

  val nemo = Fish(1, "Male", true)
  nemo.swim()

  val marty = Zebra(4, "Male", true)
  marty.run()

  println("Is Marty a mammal? ${marty.isMammal()}")
}
