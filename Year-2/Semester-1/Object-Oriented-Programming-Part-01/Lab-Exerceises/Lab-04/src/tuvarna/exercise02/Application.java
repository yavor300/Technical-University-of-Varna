package tuvarna.exercise02;

public class Application {

  public static void main(String[] args) {

    for (String arg : args) {
      long result = Calculator.arithmeticExpression(arg);
      System.out.println(result);
    }
  }
}
