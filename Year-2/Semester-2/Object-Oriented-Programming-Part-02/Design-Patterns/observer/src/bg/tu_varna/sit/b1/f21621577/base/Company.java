package bg.tu_varna.sit.b1.f21621577.base;

import java.util.ArrayList;
import java.util.List;

public abstract class Company {

  private final List<Observer> observers = new ArrayList<>();
  private final String name;
  private int stockPrice;

  protected Company(String name) {
    this.name = name;
  }

  public abstract void register(Observer observer);

  public abstract void unregister(Observer observer);

  public abstract void notifyRegisteredUsers();

  public String getName() {

    return name;
  }

  public int getStockPrice() {

    return stockPrice;
  }

  protected List<Observer> getObservers() {

    return observers;
  }

  public void setStockPrice(int stockPrice) {

    this.stockPrice = stockPrice;
    notifyRegisteredUsers();
  }

}
