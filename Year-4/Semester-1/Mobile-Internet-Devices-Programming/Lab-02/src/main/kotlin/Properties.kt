import java.lang.AssertionError
import java.util.*
import kotlin.collections.HashMap

class Address {

  var name: String = "Holmes, Sherlock"
  var street: String = "Baker"
  var city: String = "London"
  var state: String? = null
  var zip: String = "123456"
}

fun copyAddress(address: Address): Address {

  val result = Address();
  result.name = address.name
  result.street = address.street
  result.city = address.city
  result.state = address.state
  result.zip = address.zip
  return result
}

class Rectangle(var width: Int, private val height: Int) {

  val area: Int
    get() = this.width * this.height
}

class CustomGetterAndSetter(private val nameParam: String) {

  private val constant = "Name: "

  var name: String = nameParam
    get() = field.uppercase(Locale.getDefault())
    set(value) {
      field = value.trim()
    }

  var a: Int = 0
    set(value) {
      field = value * value
    }
    get() = field * field

  // Isolated field with private getter and setter
  private var b: String = "abc"

  // Field with public getter but private setter
  var c: String = "abc"
    private set

  override fun toString(): String {
    return constant + " " + this.name
  }
}

class BackingProperty {

  private var _table: MutableMap<String, Int>? = null

  val table: Map<String, Int>
    get() {
      if (_table == null) {
        _table = HashMap()
      }
      return _table ?: throw AssertionError("Set to null by another thread")
    }

  fun put(key: String, value: Int) {
    if (_table == null) {
      _table = HashMap()
    }
    _table!![key] = value
  }
}

fun main() {

  val origin = Address()
  val copy = copyAddress(origin)
  println(copy.name)

  val rectangle: Rectangle = Rectangle(3, 2)
  rectangle.area.also(::println)
  rectangle.width = 4
  rectangle.area.also(::println)

  val cgs: CustomGetterAndSetter = CustomGetterAndSetter("yavor");
  cgs.name.also(::println)
  cgs.name = "   Yavor   "
  cgs.name.also(::println)

  val bp: BackingProperty = BackingProperty()
  bp.put("yavor", 1)
  bp.table.get("yavor").also(::println)
}