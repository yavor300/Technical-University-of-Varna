package person

interface Person {

  val firstName: String
  val lastName: String
}

class Professor(override val firstName: String, override val lastName: String, val salary: Double) : Person
class Student(override val firstName: String, override val lastName: String, val major: String) : Person
