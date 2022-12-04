package bg.tu_varna.sit.task4;

public class SalaryAccount extends Account {

  public SalaryAccount(AccountHolder accountHolder, Currency currency, double balance) {
    super(accountHolder, currency, balance);
  }

  @Override
  public double calculateAccountInterest() {

    if (getAccountHolder().getAge() >= 25 && getAccountHolder().getAge() <= 62) {
      return 0.08 * getBalance();
    } else {
      return 0.04 * getBalance();
    }
  }
}
