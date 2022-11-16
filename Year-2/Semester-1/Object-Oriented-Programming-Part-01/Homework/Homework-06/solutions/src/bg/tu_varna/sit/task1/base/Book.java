package bg.tu_varna.sit.task1.base;

import bg.tu_varna.sit.task1.impl.Author;

abstract public class Book implements Margin {

  private final String title;
  private final Author author;
  private final int publishingYear;
  private final double price;
  private final boolean hasHardCover;

  protected Book(String title, Author author, int publishingYear, double price, boolean hasHardCover) {
    this.title = title;
    this.author = author;
    this.publishingYear = publishingYear;
    this.price = price;
    this.hasHardCover = hasHardCover;
  }

  protected abstract double getFinalPrice();

  @Override
  public String toString() {

    return String.format("%s:%s", author, title);
  }

  public double getPrice() {
    return price;
  }

  public boolean isHasHardCover() {
    return hasHardCover;
  }

  public int getPublishingYear() {
    return publishingYear;
  }
}
