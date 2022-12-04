package bg.tu_varna.sit.task5;

public class SavingsAccount extends Account {

  public SavingsAccount(String id, AccountType type, Currency currency, double balance) {
    super(id, type, currency, balance);
  }

  @Override
  public double calculateInterestValue() {

    if (getType() == AccountType.PERSONAL && getCurrency() == Currency.LEV) {
      return 0.15 * getBalance();
    } else if (getCurrency() == Currency.LEV) {
      return 0.05 * getBalance();
    } else if (getCurrency() == Currency.DOLLAR && getType() == AccountType.PERSONAL) {
      return 0.10 * getBalance();
    } else {
      return 0.01 * getBalance();
    }
  }
}
