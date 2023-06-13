package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Subject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProxySubject implements Subject {

  private final Subject subject;
  private final List<String> registeredUsers = new ArrayList<>(
          Arrays.asList("Admin", "Kate", "Sam")
  );

  public ProxySubject() {

    subject = new ConcreteSubject();
  }

  @Override
  public void doSomeWork(String user) {

    System.out.println("\n The proxy call is happening now.");
    System.out.println(user + " wants to invoke a proxy method.");
    if (registeredUsers.contains(user)) {
      subject.doSomeWork(user);
    } else {
      System.out.println("Sorry, " + user + ", you do not have access rights.");
    }
  }
}
