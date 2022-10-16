package tuvarna.exercise07;

public class StudentTicket extends StandardTicket {

  private final String ownerName;

  public StudentTicket(String performanceName, double price, boolean isUsed, String ownerName) {
    super(performanceName, price * 0.5, isUsed);
    this.ownerName = ownerName;
  }

  @Override
  public String toString() {
    return String.format("%s%nOwner name: %s", super.toString(), ownerName);
  }

  public String getOwnerName() {
    return ownerName;
  }
}
