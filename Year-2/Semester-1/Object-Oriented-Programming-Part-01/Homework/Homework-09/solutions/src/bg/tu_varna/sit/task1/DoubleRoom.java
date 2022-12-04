package bg.tu_varna.sit.task1;

public class DoubleRoom extends SingleRoom {

  private final boolean hasChildren;

  public DoubleRoom(double pricePerDay, Exposure exposure, int days, boolean hasChildren) {
    super(pricePerDay, exposure, days);
    this.hasChildren = hasChildren;
  }

  @Override
  public double calculateStayPrice() {

    return super.calculateStayPrice() + 10;
  }

  @Override
  public double discount() {

    if (getDays() > 5 && hasChildren) {
      return calculateStayPrice() * 0.15;
    }
    return 0;
  }

  @Override
  public String toString() {

    return String.format("%s, Has children: %s", super.toString(), hasChildren);
  }

  public boolean isHasChildren() {
    return hasChildren;
  }
}
