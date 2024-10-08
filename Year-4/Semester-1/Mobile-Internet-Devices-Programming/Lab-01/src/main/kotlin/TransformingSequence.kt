fun main() {

  val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")

  val lazyMap = decorations.asSequence().map {
    println("access: $it")
    it
  }

  println("lazy: $lazyMap")
  println("first: ${lazyMap.first()}")
  println("all: ${lazyMap.toList()}")

  val lazyMap2 = decorations.asSequence().filter { it[0] == 'p' }.map {
    println("access: $it")
    it
  }
  println("filtered: ${lazyMap2.toList()}")
}