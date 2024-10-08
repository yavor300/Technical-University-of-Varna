fun main() {

  val waterFilter: (Int) -> Int = { dirty -> dirty / 2 }
  val dirtyValue = 30
  val filteredValue = waterFilter(dirtyValue)
  println("Filtered value: $filteredValue")
  println(updateDirty(30, waterFilter))
  println(updateDirty(30, ::increasesDirty))
}

fun updateDirty(dirty: Int, operation: (Int) -> Int): Int {
  return operation(dirty)
}

fun increasesDirty(start: Int) = start + 1

