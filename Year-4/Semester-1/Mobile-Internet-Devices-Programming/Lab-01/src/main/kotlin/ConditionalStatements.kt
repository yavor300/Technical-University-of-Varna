@file:Suppress("KotlinConstantConditions")

fun main(args: Array<String>) {

  val numberOfFish = 50
  val numberOfPlants = 23

  if (numberOfFish > numberOfPlants) {
    println("Good ratio!")
  } else {
    println("Unhealthy ratio")
  }

  if (numberOfFish in 1..100) {
    println(numberOfFish)
  }

  if (numberOfFish == 0) {
    println("Empty tank")
  } else if (numberOfFish < 40) {
    println("Got fish!")
  } else {
    println("That’s a lot of fish!")
  }

  when (numberOfFish) {
    0 -> println("Empty tank")
    in 1..39 -> println("Got fish!")
    else -> println("That’s a lot of fish!")
  }
}
