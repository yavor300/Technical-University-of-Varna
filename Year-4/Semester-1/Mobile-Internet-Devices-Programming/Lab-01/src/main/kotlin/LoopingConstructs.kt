import java.util.Random;

fun main(args: Array<String>) {


}

fun randomDay() : String {
  val week = arrayOf ("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
  return week[Random().nextInt(week.size)]
}

fun swim(day: String, speed: String = "fast") {
  println("swimming $speed")
}
