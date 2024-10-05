@file:Suppress("KotlinConstantConditions")

fun main(args: Array<String>) {

  var fishFoodTreats: Int? = 6

  if (fishFoodTreats != null) {
    @Suppress("UNNECESSARY_SAFE_CALL")
    fishFoodTreats = fishFoodTreats?.dec();
    println(fishFoodTreats)
  }

  fishFoodTreats = fishFoodTreats?.dec()
  println(fishFoodTreats)

  fishFoodTreats = null
  fishFoodTreats = fishFoodTreats?.dec() ?: 0
  println(fishFoodTreats)
}
