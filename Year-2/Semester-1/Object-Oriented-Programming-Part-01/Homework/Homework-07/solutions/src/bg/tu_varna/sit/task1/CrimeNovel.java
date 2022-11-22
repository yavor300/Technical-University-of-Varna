package bg.tu_varna.sit.task1;

public class CrimeNovel extends Book {

  public CrimeNovel(String title, Author author, int publishingYear, double price, CoverType coverType) {
    super(title, author, publishingYear, price, coverType);
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