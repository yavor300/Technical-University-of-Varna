package bg.tu_varna.sit.task1;

abstract public class Book implements Margin {

  private final String title;
  private Author author;
  private final int publishingYear;
  private double price;
  private final CoverType coverType;

  public Book(String title, Author author, int publishingYear, double price, CoverType coverType) {
    this.title = title;
    setAuthor(author);
    this.publishingYear = publishingYear;
    setPrice(price);
    this.coverType = coverType;
  }

  protected abstract double getFinalPrice();

  protected boolean isHasHardCover() {

    return coverType == CoverType.hardCover;
  }

  @Override
  public String toString() {

    return String.format("%s:%s", author, title);
  }

  public double getPrice() {
    return price;
  }

  private void setPrice(double price) {

    if (price < 5) {
      throw new InvalidDataException("Book's price must be greater than 5!");
    }
    this.price = price;
  }

  public CoverType getCoverType() {
    return coverType;
  }

  public int getPublishingYear() {
    return publishingYear;
  }

  private void setAuthor(Author author) {

    if (author == null) {
      throw new InvalidDataException("Missing author!");
    }
    this.author = author;
  }


}