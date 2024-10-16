package accounts

open class BankAccount(val owner: String, var balance: Double) {

  fun deposit(amount: Double) {

    if (amount < 0) {
      throw IllegalArgumentException("Cannot deposit a negative amount.")
    }
    this.balance += amount
  }

  open fun withdrawal(amount: Double) {

    if (amount < 0) {
      throw IllegalArgumentException("Cannot withdraw a negative amount.")
    }
    if (this.balance < amount) {
      throw IllegalArgumentException("Insufficient funds for this withdrawal.")
    }
    this.balance -= amount;
  }
}

class CheckingAccount(owner: String, balance: Double, private val insufficientFundsFee: Double) :
  BankAccount(owner, balance) {

  override fun withdrawal(amount: Double) {
    try {
      super.withdrawal(amount)
    } catch (e: Exception) {
      balance -= insufficientFundsFee
      throw Exception("Insufficient funds. A fee of $insufficientFundsFee has been charged.")
    }
  }

  fun processCheck(amount: Double) {
    withdrawal(amount)
  }

}

class SavingsAccount(owner: String, balance: Double, private val annualInterestRate: Double) :
  BankAccount(owner, balance) {

  fun depositMonthlyInterest() {
    val monthlyInterest = balance * (annualInterestRate / 12) / 100
    deposit(monthlyInterest)
  }
}

fun main() {
  val checking = CheckingAccount("John Doe", 1000.0, 35.0)
  val savings = SavingsAccount("Jane Doe", 1500.0, 4.0)

  try {
    checking.processCheck(1010.0)
  } catch (e: Exception) {
    println(e.message)
  }

  savings.depositMonthlyInterest()
  println("Checking Balance: ${checking.balance}")
  println("Savings Balance: ${savings.balance}")
}
