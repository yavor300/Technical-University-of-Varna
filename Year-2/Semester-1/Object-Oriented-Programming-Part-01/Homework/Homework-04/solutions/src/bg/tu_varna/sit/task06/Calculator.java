package bg.tu_varna.sit.task06;

public class Calculator {

  public static long[] performAction(long[] arr, String action, int[] params) {
    long[] array = arr.clone();

    int pos = params[0];
    int value = params[1];

    switch (action) {
      case "multiply":
        array[pos] *= value;
        break;
      case "add":
        array[pos] += value;
        break;
      case "subtract":
        array[pos] -= value;
        break;
      case "lshift":
        ArrayWrapper.arrayShiftLeft(array);
        break;
      case "rshift":
        ArrayWrapper.arrayShiftRight(array);
        break;
    }

    return array;
  }
}
