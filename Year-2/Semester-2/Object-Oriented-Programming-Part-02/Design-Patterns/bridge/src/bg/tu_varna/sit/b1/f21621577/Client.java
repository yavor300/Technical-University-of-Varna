package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.ElectronicItem;
import bg.tu_varna.sit.b1.f21621577.entities.OnlinePrice;
import bg.tu_varna.sit.b1.f21621577.entities.ShowroomPrice;
import bg.tu_varna.sit.b1.f21621577.entities.Television;

public class Client {

  public static void main(String[] args) {

    ElectronicItem television = new Television(new OnlinePrice());
    television.showPriceDetail();
    television = new Television(new ShowroomPrice());
    television.showPriceDetail();
  }
}
