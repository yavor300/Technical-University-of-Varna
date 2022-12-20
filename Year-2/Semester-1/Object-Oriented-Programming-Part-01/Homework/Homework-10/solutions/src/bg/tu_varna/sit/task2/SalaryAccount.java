package bg.tu_varna.sit.task2;

public class SalaryAccount extends Account {

  public SalaryAccount(String id, AccountType type, Currency currency, double balance) throws InvalidDataException {
    super(id, type, currency, balance);
  }

  @Override
  public double calculateInterestValue() {

    if (getType() == AccountType.CORPORATE) {
      return 0;
    } else if (getCurrency() == Currency.LEV) {
      return 0.05 * getBalance();
    } else if (getCurrency() == Currency.DOLLAR) {
      return 0.03 * getBalance();
    } else if (getCurrency() == Currency.EURO) {
      return 0.02 * getBalance();
    } else {
      return 0.01 * getBalance();
    }
  }
}
