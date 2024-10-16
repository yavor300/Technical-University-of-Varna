package sealed

sealed class Error(val message: String) {

  class NetworkError: Error("Network failure")
  class DatabaseError : Error("Database cannot be reached")
  class UnknownError : Error("An unknown error has occurred")
}

fun main() {
  val errors = listOf(Error.NetworkError(), Error.DatabaseError(), Error.UnknownError())
  for (error in errors) {
    println(error.message)
  }
}