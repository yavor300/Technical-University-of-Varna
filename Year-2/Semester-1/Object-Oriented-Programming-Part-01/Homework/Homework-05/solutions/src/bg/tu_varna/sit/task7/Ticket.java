package bg.tu_varna.sit.task7;

abstract class Ticket {

  private String performanceName;
  protected double price;

  protected Ticket(String performanceName, double price) {
    this.performanceName = performanceName;
    this.price = price;
  }

  abstract double getPrice();

}
