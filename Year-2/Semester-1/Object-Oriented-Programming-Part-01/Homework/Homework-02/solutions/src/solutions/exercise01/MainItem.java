package exercise01;

/**
 * Задача 1
 * <p>
 * Дефинирайте клас Item, който описва стока с атрибути тип, количество,
 * единична цена и годност (брой дни). Като поведение използвайте методи за четене/запис,
 * метод за текстово описание. Създайте масив от 10 обекта.
 * Намерете и изведете средната цена.
 */
public class MainItem {

  private static final int PRODUCTS_COUNT = 10;

  public static void main(String[] args) {

    Item[] items = initializeData(PRODUCTS_COUNT);
    System.out.println("Items info:\n");
    for (Item item : items) {
      System.out.println(item + "\n");
    }

    System.out.printf("Average price for product: %.2f BGN", getAveragePriceForProduct(items));
  }

  private static Item[] initializeData(int dataCounter) {

    String[] types = {"Banana", "Apple", "Juice", "Orange", "Bread", "Butter", "Cheese", "Meat", "Milk", "Honey"};
    Item[] items = new Item[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      items[i] = new Item(
              types[getRandomIntegerNumber(0, types.length)],
              getRandomIntegerNumber(0, 60),
              getRandomDoubleNumber(3.00, 13.50),
              getRandomIntegerNumber(3, 60)
      );
    }

    return items;
  }

  private static double getAveragePriceForProduct(Item[] items) {

    double totalPrice = 0;
    int totalQuantities = 0;

    for (Item item : items) {
      totalPrice += item.getPrice() * item.getQuantity();
      totalQuantities += item.getQuantity();
    }

    return totalPrice / totalQuantities;
  }


  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private static double getRandomDoubleNumber(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }
}
