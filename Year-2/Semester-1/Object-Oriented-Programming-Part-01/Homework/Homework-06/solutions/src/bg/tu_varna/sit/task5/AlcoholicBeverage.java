package bg.tu_varna.sit.task5;

public class AlcoholicBeverage extends Drink {

  private double vol;

  public AlcoholicBeverage(String name, int quantity, int serveQuantity, double vol) {
    super(name, quantity, serveQuantity);
    this.vol = vol;
  }

  @Override
  public boolean needOfDelivery() {

    return getQuantity() < 5;
  }

  @Override
  public String toString() {

    return String.format("%s, %s", super.toString(), needOfDelivery());
  }
}
