package bg.tu_varna.sit.task1;

public class SciFiNovel extends Book {

  public SciFiNovel(String title, Author author, int publishingYear, double price, CoverType coverType) {
    super(title, author, publishingYear, price, coverType);
  }

  @Override
  protected double getFinalPrice() {

    return getPrice() + calculateMargin();
  }

  @Override
  public double calculateMargin() {

    return isHasHardCover() ? 0.09 * getPrice() : 0.12 * getPrice();
  }
}