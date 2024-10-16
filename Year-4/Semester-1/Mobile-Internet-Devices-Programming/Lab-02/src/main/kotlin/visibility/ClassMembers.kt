package visibility

open class Outer {

  private val a = 1
  protected open val b = 2
  internal open val c = 3
  val d = 4 // public by default

  protected class Nested {
    public val e: Int = 5
  }
}

class Subclass : Outer() {

  // a is not visible
  // b, c, and d are visible
  // Nested and e are visible

  override val b: Int
    get() = 5

  override val c: Int
    get() = 7
}

class C private constructor(a: Int) {}



