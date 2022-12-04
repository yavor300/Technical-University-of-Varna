package bg.tu_varna.sit.task1;

public abstract class Room implements PriceCalculator {

  private final double pricePerDay;
  private final Exposure exposure;
  private boolean available;

  protected Room(double pricePerDay, Exposure exposure) {
    this.pricePerDay = pricePerDay;
    this.exposure = exposure;
    this.available = true;
  }

  @Override
  public double calculateReservationPrice() {

    return calculateStayPrice() - discount();
  }

  @Override
  public String toString() {

    return String.format("%s: %.2f $ per day, %s, Availability: %s",
            getClass().getSimpleName(), pricePerDay, exposure, available);
  }

  protected double getPricePerDay() {
    return pricePerDay;
  }

  protected Exposure getExposure() {
    return exposure;
  }

  protected boolean isAvailable() {
    return available;
  }

  protected void setAvailable(boolean available) {
    this.available = available;
  }
}
