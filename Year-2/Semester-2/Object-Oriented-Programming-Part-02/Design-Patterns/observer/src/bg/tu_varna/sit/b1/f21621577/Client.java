package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Company;
import bg.tu_varna.sit.b1.f21621577.base.Observer;
import bg.tu_varna.sit.b1.f21621577.entities.Customer;
import bg.tu_varna.sit.b1.f21621577.entities.Employee;
import bg.tu_varna.sit.b1.f21621577.entities.SpecificCompany;

public class Client {

  public static void main(String[] args) {

    Observer roy = new Employee("Roy");
    Observer kevin = new Employee("Kevin");
    Observer bose = new Customer("Bose");
    Observer jacklin = new Customer("Jacklin");

    Company abcLtd = new SpecificCompany("ABC Ltd.");
    abcLtd.register(roy);
    abcLtd.register(kevin);
    abcLtd.register(jacklin);
    abcLtd.register(bose);

    abcLtd.setStockPrice(500);

    abcLtd.unregister(jacklin);
    abcLtd.setStockPrice(534);

  }
}
