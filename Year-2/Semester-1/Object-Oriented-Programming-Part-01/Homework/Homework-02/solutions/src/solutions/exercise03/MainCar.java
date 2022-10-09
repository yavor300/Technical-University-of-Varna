package exercise03;

/**
 * Задача 3
 * <p>
 * Дефинирайте клас Car, който описва автомобил с атрибути марка,
 * регистрационен номер и пробег, като всички атрибути са символни низове.
 * Инициализирайте обектите с параметризиран конструктор.
 * Като поведение използвайте методи за четене и текстово описание.
 * Създайте масив от 10 обекта. Намерете и изведете автомобила с най-малък пробег.
 */
public class MainCar {

  private static final int CARS_COUNT = 10;

  public static void main(String[] args) {

    Car[] cars = initializeCarsData(CARS_COUNT);
    System.out.println("Cars info:\n");
    for (Car car : cars) {
      System.out.println(car + "\n");
    }

    System.out.printf("Car with the lowest range:%n%s", getCarWithLowestRange(cars));
  }

  private static Car[] initializeCarsData(int dataCounter) {

    String[] brands = {"Tesla", "BMW", "Nissan", "Honda", "Toyota", "VW"};
    Car[] cars = new Car[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      cars[i] = new Car(
              brands[getRandomIntegerNumber(0, brands.length)],
              generateRandomCarNumber(),
              String.valueOf(getRandomIntegerNumber(100000, 300000)));
    }

    return cars;
  }

  private static Car getCarWithLowestRange(Car[] cars) {

    Car result = cars[0];
    for (int i = 1; i < cars.length; i++) {
      if (Integer.parseInt(cars[i].getRange()) < Integer.parseInt(result.getRange())) {
        result = cars[i];
      }
    }

    return result;
  }

  private static String generateRandomCarNumber() {

    String result = "";
    result += (char) getRandomIntegerNumber(65, 91) + " " +
            getRandomIntegerNumber(1000, 10000) + " " +
            (char) getRandomIntegerNumber(65, 91) +
            (char) getRandomIntegerNumber(65, 91);

    return result;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
