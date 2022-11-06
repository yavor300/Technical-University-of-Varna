package bg.tu_varna.sit.task7;

class StandardTicket extends Ticket {

  private double valid;

  StandardTicket(String performanceName, double price, double valid) {
    super(performanceName, price);
    this.valid = valid;
  }

  @Override
  double getPrice() {

    return super.price;
  }
}
