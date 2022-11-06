package bg.tu_varna.sit.task7;

class DiscountTicket extends StandardTicket {

  private String userName;

  DiscountTicket(String performanceName, double price, double valid, String userName) {
    super(performanceName, price, valid);
    this.userName = userName;
  }

  @Override
  double getPrice() {

    return super.getPrice() * 0.5;
  }
}
