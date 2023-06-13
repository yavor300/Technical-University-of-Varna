package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Subject;
import bg.tu_varna.sit.b1.f21621577.entities.ProxySubject;

public class Client {

  public static void main(String[] args) {

    Subject proxy = new ProxySubject();
    proxy.doSomeWork();
  }
}
