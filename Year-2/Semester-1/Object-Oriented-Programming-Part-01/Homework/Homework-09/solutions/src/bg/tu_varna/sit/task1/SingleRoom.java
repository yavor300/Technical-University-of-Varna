package bg.tu_varna.sit.task1;

public class SingleRoom extends Room {

  private final int days;

  public SingleRoom(double pricePerDay, Exposure exposure, int days) {
    super(pricePerDay, exposure);
    this.days = days;
  }

  @Override
  public double calculateStayPrice() {

    return days * getPricePerDay();
  }

  @Override
  public double discount() {

    if (days > 3) {
      return calculateStayPrice() * 0.1;
    }
    return 0;
  }

  @Override
  public String toString() {

    return String.format("%s, Days: %d", super.toString(), days);
  }

  public int getDays() {
    return days;
  }


}
