package bg.tu_varna.sit.b1.f21621577.implementation;

import bg.tu_varna.sit.b1.f21621577.base.extratopping.ToppingDecorator;
import bg.tu_varna.sit.b1.f21621577.base.maindish.MainDish;

import java.util.ArrayList;
import java.util.List;

public class CompleteDish {

  private final MainDish dish;
  private final List<ToppingDecorator> extras = new ArrayList<>();

  public CompleteDish(MainDish dish) {
    this.dish = dish;
  }

  public void addExtra(ToppingDecorator extra) {
    extras.add(extra);
  }

  public String getDescription() {
    String baseDescription = dish.getName();

    if (extras.isEmpty()) {
      return baseDescription;
    }

    StringBuilder sb = new StringBuilder(baseDescription);
    sb.append(" with");

    for (ToppingDecorator extra : extras) {
      sb.append(" ").append(extra.getName()).append(",");
    }

    sb.replace(sb.length() - 1, sb.length(), ".");
    return sb.toString();
  }

  public double getPrice() {
    double price = dish.getPrice();
    for (ToppingDecorator extra : extras) {
      price += extra.getPrice();
    }
    return price;
  }
}

