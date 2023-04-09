package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.extratopping.ToppingDecorator;
import bg.tu_varna.sit.b1.f21621577.base.maindish.pizza.Pizza;
import bg.tu_varna.sit.b1.f21621577.enums.DoughType;
import bg.tu_varna.sit.b1.f21621577.enums.MeatType;
import bg.tu_varna.sit.b1.f21621577.implementation.CompleteDish;
import bg.tu_varna.sit.b1.f21621577.implementation.Restaurant;
import bg.tu_varna.sit.b1.f21621577.implementation.extratopping.BaconDecorator;
import bg.tu_varna.sit.b1.f21621577.implementation.extratopping.OlivesDecorator;
import bg.tu_varna.sit.b1.f21621577.implementation.maindish.pizza.MeatPizza;

/**
 * Задача 1
 * <p>
 * Използвайте Bridge и Decorator pattern, за да разработите система за управление на ресторант.
 * <p>
 * Ресторанта има различни видове ястия - скара, пици, салати. Направете два вида скара, два вида пица и два вида салата.
 * <p>
 * Към всяко ястие може да се добавят множество екстри - сирене, бекон, маслини.
 * <p>
 * Да се състави поръчка и да се изведе крайния резултат от поръчката, като се включат всички добавени екстри за всички ястия.
 */
public class Main {

  public static void main(String[] args) {

    Pizza pizza = new MeatPizza("MeatPizza", 20.00, DoughType.GLUTEN_FREE, MeatType.BEEF);
    ToppingDecorator baconDecorator = new BaconDecorator("Bacon", 13.00, pizza);
    ToppingDecorator olivesDecorator = new OlivesDecorator("Olives", 12.00, pizza);
    System.out.println(baconDecorator.getFullMealName());
    System.out.println(olivesDecorator.getFullMealPrice());

    CompleteDish completeDish = new CompleteDish(pizza);
    completeDish.addExtra(baconDecorator);
    completeDish.addExtra(olivesDecorator);

    Restaurant restaurant = new Restaurant();
    restaurant.addDish(completeDish);
    for (CompleteDish dish : restaurant.getDishes()) {
      System.out.println(completeDish.getDescription());
      System.out.println(completeDish.getPrice());
    }
  }
}
