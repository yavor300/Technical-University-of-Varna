package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Company;
import bg.tu_varna.sit.b1.f21621577.base.Observer;

public class Customer implements Observer {

  private final String nameOfObserver;

  public Customer(String nameOfObserver) {
    this.nameOfObserver = nameOfObserver;
  }

  @Override
  public void getNotification(Company company) {

    System.out.println(nameOfObserver + " is notified from " + company.getName());
    System.out.println("Its current stock price is:$" + company.getStockPrice());
  }

  @Override
  public String getObserverName() {

    return nameOfObserver;
  }
}
