package bg.tu_varna.sit.task1.impl;

import bg.tu_varna.sit.task1.base.Book;

public class PoetryBook extends Book {

  public PoetryBook(String title, Author author, int publishingYear, double price, boolean hasHardCover) {
    super(title, author, publishingYear, price, hasHardCover);
  }

  @Override
  protected double getFinalPrice() {

    return getPrice() + calculateMargin();
  }

  @Override
  public double calculateMargin() {

    return isHasHardCover() && getPublishingYear() > 2000 ? 0.01 * getPrice() : 0.05 * getPrice();
  }
}
