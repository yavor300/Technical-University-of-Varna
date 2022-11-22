package bg.tu_varna.sit.task5;

public class AlcoholicBeverage extends Drink {

  private final double vol;

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

  public boolean isProper(int age) throws AlcoholicBeverageException {

    if (age <= 0) {
      throw new AlcoholicBeverageException("Въведена е невалидна възраст");
    }

    if (age < 18) {
      throw new AlcoholicBeverageException("Лица под 18 г. не могат да консумират алкохолни напитки");
    }

    if (age < 21 || age > 70) {
      return vol > 10;
    }

    return true;
  }
}
