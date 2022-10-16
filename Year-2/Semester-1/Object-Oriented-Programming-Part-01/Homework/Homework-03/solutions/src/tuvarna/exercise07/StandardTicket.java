package tuvarna.exercise07;

public class StandardTicket extends Ticket {

  private final boolean isUsed;

  public StandardTicket(String performanceName, double price, boolean isUsed) {
    super(performanceName, price);
    this.isUsed = isUsed;
  }

  @Override
  public String toString() {
    return String.format("%s%nIs used: %s", super.toString(), isUsed);
  }

  public boolean isUsed() {
    return isUsed;
  }
}
