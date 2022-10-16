package tuvarna.exercise01;

public class Printer extends Machine {

  private int numberOfPages;

  public Printer(double price, int numberOfPages) {
    super(price);
    this.numberOfPages = numberOfPages;
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  public void setNumberOfPages(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  @Override
  public String toString() {
    return String.format("Price: %.2f BGN.%nNumber of pages: %d", getPrice(), getNumberOfPages());
  }
}
