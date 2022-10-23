package tuvarna.exercise02;

public class Calculator {

  public static long arithmeticExpression(String expression) {

    long result = 0;

    while (!expression.equals("END")) {

      String[] codeArgs = expression.split(" ");
      switch (codeArgs[0]) {
        case "INC": {
          int operandOne = Integer.parseInt(codeArgs[1]);
          result = ++operandOne;
          break;
        }
        case "DEC": {
          int operandOne = Integer.parseInt(codeArgs[1]);
          result = operandOne - 1;
          break;
        }

        case "ADD": {
          int operandOne = Integer.parseInt(codeArgs[1]);
          int operandTwo = Integer.parseInt(codeArgs[2]);
          result = operandOne + operandTwo;
          break;
        }

        case "MLA": {
          int operandOne = Integer.parseInt(codeArgs[1]);
          int operandTwo = Integer.parseInt(codeArgs[2]);
          result = (long) operandOne * operandTwo;
          break;
        }

        default:
          break;
      }
      expression = codeArgs[codeArgs.length - 1];
    }

    return result;
  }
}
