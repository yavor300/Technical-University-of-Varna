package tuvarna.exercise01;

public class Calculator {

  public static double getAverage(double[] array) {

    double sum = 0;
    for (int i = 0; i < array.length; i++) {
      sum += array[i];
    }

    return sum / array.length;
  }
}