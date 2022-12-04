package bg.tu_varna.sit.task5;

public abstract class Account implements InterestCalculator {

  private final String id;
  private final AccountType type;
  private final Currency currency;
  private final double balance;

  protected Account(String id, AccountType type, Currency currency, double balance) {
    this.id = id;
    this.type = type;
    this.currency = currency;
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Account account = (Account) o;

    return id.equals(account.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Account{" +
            "id='" + id + '\'' +
            ", type=" + type +
            ", currency=" + currency +
            ", balance=" + balance +
            '}';
  }

  protected String getId() {
    return id;
  }

  protected AccountType getType() {
    return type;
  }

  protected Currency getCurrency() {
    return currency;
  }

  protected double getBalance() {
    return balance;
  }

}
