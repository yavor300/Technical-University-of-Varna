package tuvarna.exercise01;

public class Machine {

  private double price;

  protected Machine(double price) {
    this.price = price;
  }

  protected double getPrice() {
    return price;
  }

  protected void setPrice(double price) {
    this.price = price;
  }
}
