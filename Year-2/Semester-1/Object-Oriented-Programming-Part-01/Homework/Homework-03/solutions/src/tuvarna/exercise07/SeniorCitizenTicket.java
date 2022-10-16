package tuvarna.exercise07;

public class SeniorCitizenTicket extends StandardTicket {

  private final String ownerName;

  public SeniorCitizenTicket(String performanceName, double price, boolean isUsed, String ownerName) {
    super(performanceName, price, isUsed);
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
