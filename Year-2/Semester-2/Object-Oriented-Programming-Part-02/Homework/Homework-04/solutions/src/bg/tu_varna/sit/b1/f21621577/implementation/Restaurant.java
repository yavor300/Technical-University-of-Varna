package bg.tu_varna.sit.b1.f21621577.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Restaurant {

  private List<CompleteDish> dishes = new ArrayList<>();

  public Restaurant() {
  }

  public Restaurant(List<CompleteDish> dishes) {
    this.dishes = dishes;
  }

  public void addDish(CompleteDish completeDish) {
    dishes.add(completeDish);
  }

  public List<CompleteDish> getDishes() {
    return Collections.unmodifiableList(dishes);
  }
}
