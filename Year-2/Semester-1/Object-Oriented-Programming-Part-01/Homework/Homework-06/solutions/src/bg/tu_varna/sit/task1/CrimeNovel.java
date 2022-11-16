package bg.tu_varna.sit.task1;

public class CrimeNovel extends Book {

  public CrimeNovel(String title, Author author, int publishingYear, double price, boolean hasHardCover) {
    super(title, author, publishingYear, price, hasHardCover);
  }

  @Override
  public double getFinalPrice() {

    return getPrice() + calculateMargin();
  }

  @Override
  public double calculateMargin() {

    return isHasHardCover() ? 0.02 * getPrice() : 0.01 * getPrice();
  }
}
