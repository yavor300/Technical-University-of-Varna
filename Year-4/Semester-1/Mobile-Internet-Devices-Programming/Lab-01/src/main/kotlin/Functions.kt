import java.util.Random;

fun main(args: Array<String>) {

  val randomDay = randomDay()
  swim(randomDay)
}

fun randomDay(): String {
  val week = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
  return week[Random().nextInt(week.size)]
}

fun swim(day: String, speed: String = "fast") {
  println("Swimming on $day with $speed tempo")
}
