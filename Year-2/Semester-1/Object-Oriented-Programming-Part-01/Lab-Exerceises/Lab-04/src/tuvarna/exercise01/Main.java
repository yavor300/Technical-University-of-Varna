package tuvarna.exercise01;

public class Main {

  public static void main(String[] args) {

    double[] grades = new double[]{5, 3.5, 4.44, 6.00, 2.20, 3.11};
    double average = Calculator.getAverage(grades);
    System.out.println(average);
  }
}
