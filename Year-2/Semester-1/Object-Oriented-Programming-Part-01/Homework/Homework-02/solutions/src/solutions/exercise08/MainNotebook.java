package exercise08;

/**
 * Задача 8
 * <p>
 * Дефинирайте клас Notebook, който описва тетрадка с атрибути брой страници,
 * цена и дали тетрадката е с твърди корици или не.
 * Инициализирайте обектите с параметризиран конструктор.
 * Като поведение използвайте методи за четене, текстово описание и равенство (по всички атрибути).
 * Създайте масив от 10 обекта.
 * Намерете и изведете брой тетрадки с меки корици и средната цена на тетрадките.
 */
public class MainNotebook {

  private static final int NOTEBOOKS_COUNT = 10;

  public static void main(String[] args) {

    Notebook[] notebooks = initializeNotebooksData(NOTEBOOKS_COUNT);
    System.out.println("Notebooks info:\n");
    for (Notebook notebook : notebooks) {
      System.out.println(notebook + "\n");
    }

    System.out.printf("Notebooks with paperback count: %d%n" +
                    "Average notebook price: %.2f BGN",
            getNotebooksWithPaperbackCount(notebooks),
            getAverageNotebookPrice(notebooks));
  }

  private static Notebook[] initializeNotebooksData(int dataCounter) {

    Notebook[] notebooks = new Notebook[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      notebooks[i] = new Notebook(
              getRandomIntegerNumber(200, 400),
              getRandomDoubleNumber(10, 25),
              i % 2 == 0
      );

      for (int j = 0; j < i; j++) {
        if (notebooks[i].equals(notebooks[j])) {
          i--;
          break;
        }
      }
    }

    return notebooks;
  }

  private static int getNotebooksWithPaperbackCount(Notebook[] notebooks) {

    int result = 0;
    for (Notebook notebook : notebooks) {
      if (notebook.isWithHardCovers()) {
        result++;
      }
    }

    return result;
  }

  private static double getAverageNotebookPrice(Notebook[] notebooks) {

    double totalPrice = 0;
    for (Notebook notebook : notebooks) {
      totalPrice += notebook.getPrice();
    }

    return totalPrice / notebooks.length;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private static double getRandomDoubleNumber(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }
}
