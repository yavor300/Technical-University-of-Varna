package exercise02;

/**
 * Задача 2
 *
 * Дефинирайте клас Apartment, който описва апартамент с атрибути етаж, площ, брой стаи,
 * изложение. Инициализирайте обектите с параметризиран конструктор. Като поведение използвайте
 * методи за четене, текстово описание и равенство (по площ). Създайте масив от 10 обекта.
 * Намерете и изведете апартамента с най-голяма площ.
 */
public class Main {

  public static void main(String[] args) {

    Apartment[] apartments = initializeData(10);
    for (Apartment apartment : apartments) {
      System.out.println(apartment);
    }
    System.out.printf("Biggest apartment: %s", getBiggestApartment(apartments));
  }

  private static Apartment[] initializeData(int dataCounter) {

    String[] locations = {"EAST", "WEST", "SOUTH", "NORTH"};
    Apartment[] apartments = new Apartment[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      apartments[i] = new Apartment(
              getRandomIntegerNumber(1, 10),
              getRandomDoubleNumber(40.00, 108.00),
              getRandomIntegerNumber(1, 4),
              locations[getRandomIntegerNumber(0, 3)]);
    }

    return apartments;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private static double getRandomDoubleNumber(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }

  private static Apartment getBiggestApartment(Apartment[] apartments) {
    Apartment result = apartments[0];
    for (int i = 1; i < apartments.length; i++) {
      if (apartments[i].getSquareFootage() > result.getSquareFootage()) {
        result = apartments[i];
      }
    }
    return result;
  }
}

