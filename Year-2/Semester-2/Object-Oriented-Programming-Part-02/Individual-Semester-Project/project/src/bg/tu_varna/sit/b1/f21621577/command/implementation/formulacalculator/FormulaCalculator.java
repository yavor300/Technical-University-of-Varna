package bg.tu_varna.sit.b1.f21621577.command.implementation.formulacalculator;

import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;

import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isFractionalNumber;
import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isInteger;

/**
 * A class that can evaluate formulas using the reverse Polish notation algorithm.
 * This class uses a singleton pattern and can be obtained through the {@link #getInstance()} method.
 */
public class FormulaCalculator {

  private static FormulaCalculator instance = null;
  private final Pattern cellRefPattern = Pattern.compile("R(\\d+)C(\\d+)");

  /**
   * Private constructor to prevent external instantiation
   */
  private FormulaCalculator() {
  }

  /**
   * Returns the singleton instance of the {@code FormulaCalculator} class.
   *
   * @return The singleton instance of the {@code FormulaCalculator} class.
   */
  public static FormulaCalculator getInstance() {
    if (instance == null) {
      instance = new FormulaCalculator();
    }
    return instance;
  }

  /**
   * Evaluates the given formula and returns the result.
   *
   * @param formula The formula to evaluate.
   * @return The result of the formula.
   * @throws ArithmeticException      If the formula contains division by zero.
   * @throws IllegalArgumentException If the formula contains an invalid operator.
   */
  public double evaluate(String formula) {

    List<String> tokens = tokenize(formula);
    Deque<Double> values = new ArrayDeque<>();
    Deque<Character> ops = new ArrayDeque<>();

    for (String token : tokens) {
      if (isCellReference(token)) {
        double cellValue = getCellValue(token);
        values.push(cellValue);
      } else if (isInteger(token) || isFractionalNumber(token)) {
        values.push(Double.parseDouble(token));
      } else if (isOperator(token)) {
        char op = token.charAt(0);
        while (!ops.isEmpty() && hasPrecedence(ops.peek(), op)) {
          double b = values.pop();
          double a = values.pop();
          char prevOp = ops.pop();
          double result = applyOperation(prevOp, b, a);
          values.push(result);
        }
        ops.push(op);
      }
    }

    while (!ops.isEmpty()) {
      double b = values.pop();
      double a = values.pop();
      char op = ops.pop();
      double result = applyOperation(op, b, a);
      values.push(result);
    }

    return values.pop();
  }


  /**
   * Tokenizes a given formula string into a list of tokens.
   *
   * @param formula The formula string to tokenize.
   * @return A list of tokens extracted from the formula string.
   */
  private List<String> tokenize(String formula) {

    List<String> tokens = new ArrayList<>();
    StringBuilder currentToken = new StringBuilder();

    for (int i = 0; i < formula.length(); i++) {
      char currentChar = formula.charAt(i);

      if (Character.isDigit(currentChar) || currentChar == '.') {
        currentToken.append(currentChar);
      } else if (Character.isAlphabetic(currentChar)) {
        currentToken.append(currentChar);
      } else {
        // Add the current token if it's not empty
        if (currentToken.length() > 0) {
          tokens.add(currentToken.toString());
          currentToken.setLength(0);
        }

        // Add the current operator or symbol as a separate token
        tokens.add(Character.toString(currentChar));
      }
    }

    // Add the last token if it's not empty
    if (currentToken.length() > 0) {
      tokens.add(currentToken.toString());
    }

    // Remove the initial "=" symbol
    if (tokens.size() > 0 && tokens.get(0).equals("=")) {
      tokens.remove(0);
    }

    return tokens;
  }

  /**
   * Checks if a given token is a valid cell reference.
   *
   * @param token The token to check
   * @return true if the token is a valid cell reference, false otherwise
   */
  private boolean isCellReference(String token) {
    return cellRefPattern.matcher(token).matches();
  }

  /**
   * Returns the numerical value of a given cell reference in the table.
   *
   * @param cellRef the reference to the cell, in the format "RxCy", where x is the row number and y is the column number
   * @return the numerical value of the cell, or 0 if the cell value is not a valid number
   */
  private double getCellValue(String cellRef) {

    String[] parts = cellRef.split("R|C");
    int row = Integer.parseInt(parts[1]) - 1;
    int col = Integer.parseInt(parts[2]) - 1;

    TableCell cell = TableRepository.getInstance().getCell(row, col);

    if (isInteger(cell.getValueAsString()) || isFractionalNumber(cell.getValueAsString())) {
      return Double.parseDouble(cell.getValueAsString());
    }

    return 0;
  }

  /**
   * Determines if the given token is an arithmetic operator.
   *
   * @param token the token to be checked
   * @return true if the token is an operator (+, -, *, /, or ^); false otherwise
   */
  private boolean isOperator(String token) {
    return token.length() == 1 && "+-*/^".contains(token);
  }


  /**
   * Determines whether the first operator has higher precedence than the second operator.
   * Precedence rules:
   * Exponentiation (^) has the highest precedence
   * Multiplication (*) and division (/) have higher precedence than addition (+) and subtraction (-)
   *
   * @param op1 the first operator
   * @param op2 the second operator
   * @return true if the first operator has higher precedence than the second operator, false otherwise
   */
  private boolean hasPrecedence(char op1, char op2) {

    if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
      return true;
    }
    return (op1 == '^') && (op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-');
  }

  /**
   * Applies the given operation to the two operands.
   *
   * @param op the operator to be applied
   * @param b  the second operand
   * @param a  the first operand
   * @return the result of the operation
   * @throws ArithmeticException      if division by zero is attempted
   * @throws IllegalArgumentException if an invalid operator is provided
   */
  private double applyOperation(char op, double b, double a) {
    switch (op) {
      case '+':
        return a + b;
      case '-':
        return a - b;
      case '*':
        return a * b;
      case '/':
        if (b == 0) {
          throw new ArithmeticException("Division by zero!");
        }
        return a / b;
      case '^':
        return Math.pow(a, b);
      default:
        throw new IllegalArgumentException("Invalid operator: " + op);
    }
  }
}