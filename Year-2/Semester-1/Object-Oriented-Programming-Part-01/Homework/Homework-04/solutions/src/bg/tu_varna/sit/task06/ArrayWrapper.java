package bg.tu_varna.sit.task06;

public class ArrayWrapper {

  public static void arrayShiftRight(long[] array) {
    for (int i = array.length - 1; i >= 1; i--) {
      array[i] = array[i - 1];
    }
  }

  public static void arrayShiftLeft(long[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      array[i] = array[i + 1];
    }
  }

  public static void printArray(long[] array) {
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
  }
}
