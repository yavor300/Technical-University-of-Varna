package exercise08;

public class Notebook {

  private int pages;
  private double price;
  private boolean isWithHardCovers;

  public Notebook(int pages, double price, boolean isWithHardCovers) {
    this.pages = pages;
    this.price = price;
    this.isWithHardCovers = isWithHardCovers;
  }

  @Override
  public String toString() {
    return String.format("Pages: %d%nPrice: %.2f BGN%nHard covers: %s",
            getPages(), getPrice(), isWithHardCovers() ? "Yes" : "No");
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Notebook other = (Notebook) o;

    if (getPages() == (other.getPages())) return false;
    if (Math.abs(getPrice() - other.getPrice()) >= 0.01) return false;
    return isWithHardCovers() == (other.isWithHardCovers());
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isWithHardCovers() {
    return isWithHardCovers;
  }

  public void setWithHardCovers(boolean withHardCovers) {
    isWithHardCovers = withHardCovers;
  }
}
