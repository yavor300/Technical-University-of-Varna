fun main(args: Array<String>) {

  val schoolList = listOf("mackerel", "trout", "halibut")
  println(schoolList)

  val schoolMutableList = mutableListOf("mackerel", "trout", "halibut")
  println(schoolMutableList)
  schoolMutableList.add("element")
  println(schoolMutableList)

  val schoolArrayOf = arrayOf("shark", "salmon", "minnow")
  println(schoolArrayOf.contentToString())

  val mix = arrayOf("fish", 2)
  println(mix.contentToString())

  val numbers = intArrayOf(1, 2, 3)
  println(numbers.contentToString())

  val additionalNumbers = intArrayOf(4, 5, 6)
  val merged = numbers + additionalNumbers
  println(merged.contentToString())
  println(merged[4])

  val oceans = listOf("Atlantic", "Pacific")
  val mergedDifferentTypes = listOf(numbers.contentToString(), oceans, "salmon")
  println(mergedDifferentTypes)

  /*
  * In Kotlin, 'it' is an implicit name for the single parameter of a
  * lambda expression when its context is clear and no other parameters are defined.
  */
  val array = Array(5) { it * 2 }
  println(array.contentToString())
}
