fun main(args: Array<String>) {

  val fishes = arrayOf("shark", "salmon", "minnow")

  for (element in fishes) {
    print("$element ")
  }
  println()

  for ((index, element) in fishes.withIndex()) {
    println("Item at $index is $element\n");
  }

  for (i in 1..5) print(i)
  println()

  for (i in 5 downTo 1) print(i)
  println()

  for (i in 3..6 step 2) print(i)
  println()

  for (i in 'd'..'g') print(i)

  var bubbles = 0
  while (bubbles < 50) bubbles++;
  println("$bubbles bubbles in the water\n")

  do {
    bubbles--;
  } while (bubbles > 50)
  println("$bubbles bubbles in the water\n")

  repeat(2) {
    println("A fish is swimming")
  }
}
