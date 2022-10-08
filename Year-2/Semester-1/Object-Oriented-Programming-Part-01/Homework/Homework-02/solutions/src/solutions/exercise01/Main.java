package exercise01;

/**
 * Задача 1
 *
 * Дефинирайте клас exercise01.Item, който описва стока с атрибути тип, количество,
 * единична цена и годност (брой дни). Като поведение използвайте методи за четене/запис,
 * метод за текстово описание. Създайте масив от 10 обекта.
 * Намерете и изведете средната цена.
 */
public class Main {
  public static void main(String[] args) {
    Item[] items = {
            new Item("Product1", 10, 5.99, 3),
            new Item("Product2", 10, 2.99, 30),
            new Item("Product3", 15, 3.00, 50),
            new Item("Product4", 15, 3.10, 60),
            new Item("Product5", 15, 6.00, 30),
            new Item("Product6", 15, 3.00, 30),
            new Item("Product7", 15, 7.30, 30),
            new Item("Product8", 15, 3.00, 30),
            new Item("Product9", 15, 3.20, 30),
            new Item("Product10", 15, 3.00, 30),
    };

    double totalPrice = 0;
    int totalQuantities = 0;

    for (Item item : items) {
      totalPrice += item.getPrice() * item.getQuantity();
      totalQuantities += item.getQuantity();
    }
    System.out.printf("Average price: %.2f", totalPrice / totalQuantities);
  }
}
