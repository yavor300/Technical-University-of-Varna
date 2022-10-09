package exercise09;

/**
 * Задача 9
 * <p>
 * Дефинирайте клас SoftDrink, който описва бeзалкохолна напитка с атрибути
 * наименование, срок на годност, обем на бутилката и количество захар на 100 мл.
 * Всички атрибути са символни низове. Инициализирайте обектите с параметризиран конструктор.
 * Като поведение използвайте методи за четене, текстово описание и равенство
 * (по наименование и количество захар). Създайте масив от 10 обекта.
 * Намерете и изведете средната стойност на захарта в напитките.
 */
public class Main {

  private static final Integer DRINKS_COUNT = 10;

  public static void main(String[] args) {

    SoftDrink[] softDrinks = initializeDrinksData(DRINKS_COUNT);
    System.out.println("Soft drinks info:\n");
    for (SoftDrink softDrink : softDrinks) {
      System.out.println(softDrink + "\n");
    }

    System.out.printf("Average sugar in all drinks: %.2f per 100 ml.",
            getAverageSugar(softDrinks));
  }

  private static SoftDrink[] initializeDrinksData(int dataCounter) {

    String[] drinkNames = {"Coca-Cola", "Pepsi", "Red Bull", "Hell", "Monster", "Sprite"};
    SoftDrink[] softDrinks = new SoftDrink[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      softDrinks[i] = new SoftDrink(
              drinkNames[getRandomIntegerNumber(0, drinkNames.length)],
              getRandomIntegerNumber(1, 31) + "/"
                      + getRandomIntegerNumber(1, 13) + "/"
                      + getRandomIntegerNumber(2022, 2025),
              String.valueOf(getRandomIntegerNumber(300, 500)),
              String.valueOf(getRandomDoubleNumber(10, 100))
      );

      for (int j = 0; j < i; j++) {
        if (softDrinks[i].equals(softDrinks[j])) {
          i--;
          break;
        }
      }
    }

    return softDrinks;
  }

  private static double getAverageSugar(SoftDrink[] softDrinks) {

    double totalSugar = 0;
    for (SoftDrink softDrink : softDrinks) {
      totalSugar += Double.parseDouble(softDrink.getSugar());
    }

    return totalSugar / softDrinks.length;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private static double getRandomDoubleNumber(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }
}
