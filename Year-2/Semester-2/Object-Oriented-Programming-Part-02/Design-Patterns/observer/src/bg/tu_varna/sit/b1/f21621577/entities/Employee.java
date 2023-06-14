package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Company;
import bg.tu_varna.sit.b1.f21621577.base.Observer;

public class Employee implements Observer {

  private final String nameOfObserver;

  public Employee(String nameOfObserver) {

    this.nameOfObserver = nameOfObserver;
  }

  @Override
  public void getNotification(Company company) {

    System.out.println(nameOfObserver + " has received an alert from " + company.getName());
    System.out.println("The current stock price is:$" + company.getStockPrice());
  }

  @Override
  public String getObserverName() {

    return nameOfObserver;
  }
}
