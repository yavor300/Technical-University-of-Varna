package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Company;
import bg.tu_varna.sit.b1.f21621577.base.Observer;

public class SpecificCompany extends Company {

  public SpecificCompany(String name) {
    super(name);
  }

  @Override
  public void register(Observer observer) {

    getObservers().add(observer);
    System.out.println(getName() + " registers " + observer.getObserverName());
  }

  @Override
  public void unregister(Observer observer) {

    getObservers().remove(observer);
    System.out.println(getName() + " unregisters " + observer.getObserverName());
  }

  @Override
  public void notifyRegisteredUsers() {

    for (Observer observer : getObservers()) {
      observer.getNotification(this);
    }
  }
}
