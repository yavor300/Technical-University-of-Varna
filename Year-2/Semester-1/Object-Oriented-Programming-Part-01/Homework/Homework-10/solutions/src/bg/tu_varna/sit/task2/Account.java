package bg.tu_varna.sit.task2;

public abstract class Account implements InterestCalculator {

  private final String id;
  private final AccountType type;
  private final Currency currency;
  private double balance;

  protected Account(String id, AccountType type, Currency currency, double balance) throws InvalidDataException {
    this.id = id;
    this.type = type;
    this.currency = currency;
    setBalance(balance);
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
    return getClass().getSimpleName() + "{" +
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

  public void setBalance(double balance) throws InvalidDataException {

    if (balance < 0) {
      throw new InvalidDataException();
    }
    this.balance = balance;
  }
}
