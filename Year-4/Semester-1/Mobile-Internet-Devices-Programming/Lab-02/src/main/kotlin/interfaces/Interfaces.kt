package interfaces

interface MyInterface {

  val prop: Int // abstract

  val propertyWithImplementation: String
    get() = "foo"

  fun foo() {
    println(prop)
  }

  fun bar()
}

class Child : MyInterface {

  override val prop: Int
    get() = 29

  override fun bar() {
    println("bar")
  }
}