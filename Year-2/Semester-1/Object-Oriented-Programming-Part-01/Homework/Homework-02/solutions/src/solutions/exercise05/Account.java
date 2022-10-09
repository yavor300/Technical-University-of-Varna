package exercise05;

public class Account {

  private String id;
  private String currency;
  private String balance;
  private String type;

  public Account(String id, String currency, String balance, String type) {
    this.id = id;
    this.currency = currency;
    this.balance = balance;
    this.type = type;
  }

  @Override
  public String toString() {
    return String.format("Id: %s%nCurrency: %s%nBalance: %.2f %s%nType: %s",
            getId(), getCurrency(), Double.parseDouble(getBalance()), getCurrency(), getType());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Account other = (Account) o;

    if (!getCurrency().equals(other.getCurrency())) return false;
    return getBalance().equals(other.getBalance());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
