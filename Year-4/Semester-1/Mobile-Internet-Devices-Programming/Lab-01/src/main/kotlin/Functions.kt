import java.util.Random;

fun main(args: Array<String>) {

  val randomDay = randomDay()
  swim(randomDay)
  println(fishFood(randomDay))
}

fun randomDay(): String {
  val week = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
  return week[Random().nextInt(week.size)]
}

fun swim(day: String, speed: String = "fast") {
  println("Swimming on $day with $speed tempo")
}

fun fishFood (day : String) : String {
  // Direct return on 'when' conditional statement
  return when (day) {
    "Monday" -> "flakes"
    "Wednesday" -> "redworms"
    "Thursday" -> "granules"
    "Friday" -> "mosquitoes"
    "Sunday" -> "plankton"
    else -> "nothing"
  }
}
