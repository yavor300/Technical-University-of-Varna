package bg.tu_varna.sit.task2;

public class DepositAccount extends Account {

  public DepositAccount(String id, AccountType type, Currency currency, double balance) throws InvalidDataException {
    super(id, type, currency, balance);
  }

  @Override
  public double calculateInterestValue() {

    if (getType() == AccountType.PERSONAL) {
      return 0.09 * getBalance();
    } else if (getCurrency() == Currency.LEV) {
      return 0.07 * getBalance();
    } else if (getCurrency() == Currency.DOLLAR) {
      return 0.05 * getBalance();
    } else if (getCurrency() == Currency.EURO) {
      return 0.02 * getBalance();
    } else {
      return 0.01 * getBalance();
    }
  }
}
