fun isTooHot(temperature: Int): Boolean = temperature > 30

fun isDirty(dirty: Int): Boolean = dirty > 30

fun isSunday(day: String): Boolean = day == "Sunday"

fun changeWater(day: String, temperature: Int = 22, dirty: Int = 20): Boolean {
  return when {
    isTooHot(temperature) -> true
    isDirty(dirty) -> true
    isSunday(day) -> true
    else -> false
  }
}

fun main(args: Array<String>) {
  println(changeWater("Sunday", 27))
}
