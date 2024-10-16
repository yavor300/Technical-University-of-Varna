package data

data class Person(val name: String) {
  var age: Int = 0
}

fun main() {
  val person1 = Person("John")
  val person2 = Person("John")
  person1.age = 10
  person2.age = 20

  println("person1 == person2: ${person1 == person2}")
  println("person1 with age ${person1.age}: ${person1}")
  println("person2 with age ${person2.age}: ${person2}")
  val copy = person1.copy(name = "Peter").also { println(it) }
  val (name) = copy
  println(name)
}
