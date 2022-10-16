package tuvarna.exercise07;

public class GroupTicket extends Ticket {

  private static final int VIEWER_LIMIT = 20;
  private static final int DISCOUNT_PER_VIEWER = 2;

  private int viewers;

  public GroupTicket(String performanceName, double price) {
    super(performanceName, price * 20);
    this.viewers = 0;
  }

  public void addViewer() {

    if (this.viewers == VIEWER_LIMIT) {
      throw new IllegalArgumentException("The viewer limit has reached 0.");
    }

    this.setPrice(this.getPrice() - DISCOUNT_PER_VIEWER);
    this.viewers++;
  }

  @Override
  public String toString() {
    return String.format("%s%nViewers: %d%nPrice per viewer: %.2f BGN.",
            super.toString(), viewers, getPrice() / viewers);
  }

  public int getViewers() {
    return viewers;
  }
}
