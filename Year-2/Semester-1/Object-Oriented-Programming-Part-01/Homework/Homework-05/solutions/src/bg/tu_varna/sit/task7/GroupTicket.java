package bg.tu_varna.sit.task7;

class GroupTicket extends StandardTicket {

  private final int userCount;
  private int currentUserCount;

  GroupTicket(String performanceName, double pricePerUser, double valid, int userCount, int currentUserCount) {
    super(performanceName, pricePerUser, valid);
    this.userCount = userCount;
    this.currentUserCount = currentUserCount;
  }

  void addUser() {

    if (currentUserCount + 1 <= userCount) {
      currentUserCount++;
    }
  }

  @Override
  double getPrice() {

    return super.getPrice() * userCount - (currentUserCount * getPrice() * 0.1);
  }
}
