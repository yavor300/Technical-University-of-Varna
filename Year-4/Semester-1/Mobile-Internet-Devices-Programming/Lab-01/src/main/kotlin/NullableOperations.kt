@file:Suppress("KotlinConstantConditions", "UNNECESSARY_NOT_NULL_ASSERTION", "UNNECESSARY_SAFE_CALL")

fun main(args: Array<String>) {

  var fishFoodTreats: Int? = 6

  if (fishFoodTreats != null) {
    fishFoodTreats = fishFoodTreats?.dec();
    println(fishFoodTreats)
  }

  fishFoodTreats = fishFoodTreats?.dec()
  println(fishFoodTreats)

  fishFoodTreats = null
  fishFoodTreats = fishFoodTreats?.dec() ?: 0
  println(fishFoodTreats)

  val message = "Demo message"
  val length = message!!.length
  println(length)
}
