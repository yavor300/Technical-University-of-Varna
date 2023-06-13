package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Subject;

public class ProxySubject implements Subject {

  private final Subject subject;

  public ProxySubject() {

    subject = new ConcreteSubject();
  }

  @Override
  public void doSomeWork() {

    System.out.println("The proxy call is happening now.");
    subject.doSomeWork();
  }
}
