package bg.tu_varna.sit.task4;

public class Interest<T extends  Account> {

  private final T account;

  public Interest(T account) {
    this.account = account;
  }

  public void displayAccountInterest() {

    System.out.println(account.calculateAccountInterest());
  }
}
