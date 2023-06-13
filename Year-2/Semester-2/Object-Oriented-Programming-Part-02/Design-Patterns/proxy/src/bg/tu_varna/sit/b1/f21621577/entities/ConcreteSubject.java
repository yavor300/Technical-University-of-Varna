package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Subject;

class ConcreteSubject implements Subject {

  ConcreteSubject() { }

  @Override
  public void doSomeWork(String user) {

    System.out.println(user + " invokes the method-doSomeWork().");
  }
}
