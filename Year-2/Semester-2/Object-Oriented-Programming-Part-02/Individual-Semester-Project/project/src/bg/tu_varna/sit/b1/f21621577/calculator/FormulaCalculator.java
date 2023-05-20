package bg.tu_varna.sit.b1.f21621577.calculator;

import static bg.tu_varna.sit.b1.f21621577.constants.Messages.DIVISION_BY_ZERO_ERROR_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.INVALID_OPERATOR_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.CELL_REFERENCE_PATTERN;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.INTEGER_OR_FRACTIONAL_NUMBER_PATTERN;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.MATH_ALLOWED_OPERATORS;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.ROW_OR_COLUMN_PATTERN;
import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isFractionalNumber;
import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isInteger;
import java.util.regex.Pattern;

/**
 * A class that can evaluate formulas using the reverse Polish notation algorithm.
 * This class uses a singleton pattern and can be obtained through the {@link #getInstance()} method.
 * <p>
 * Клас, който може да изчислява формули, използвайки алгоритъма за обратна полска нотация.
 * Този клас използва единичен шаблон и може да бъде получен чрез метода {@link #getInstance()}.
 */
public class FormulaCalculator {

  /**
   * A singleton instance of the {@link FormulaCalculator} class.
   * <p>
   * Единична инстанциа на класа {@link FormulaCalculator}.
   */
  private static FormulaCalculator instance = null;

  /**
   * This pattern matches strings that start with either an uppercase or lowercase 'R', followed by one or more digits,
   * then followed by either an uppercase or lowercase 'C', and then followed by one or more digits.
   * <p>
   * Този модел съвпада с низове, които започват с главна или малка буква 'R', последвани от една или повече цифри,
   * последвани от главна или малка буква 'C' и след това последвани от една или повече цифри.
   */
  private final Pattern cellRefPattern = Pattern.compile(CELL_REFERENCE_PATTERN);

  /**
   * Private constructor to prevent external instantiation
   * <p>
   * Частен конструктор за предотвратяване на външно инстанциране
   */
  private FormulaCalculator() {
  }

  /**
   * Returns the singleton instance of the {@code FormulaCalculator} class.
   * <p>
   * Връща единствената инстанция на класа {@code FormulaCalculator}.
   *
   * @return The singleton instance of the {@code FormulaCalculator} class.
   * <p>
   * Единичната инстанция на класа {@code FormulaCalculator}.
   */
  public static FormulaCalculator getInstance() {

    if (instance == null) {
      instance = new FormulaCalculator();
    }

    return instance;
  }

  /**
   * Evaluates the given formula and returns the result.
   * It takes the formula as an input and returns the result of the calculation.
   * The algorithm uses a stack-based approach to parse the formula, where operands and
   * operators are pushed and popped from two separate stacks.
   * The input formula is first tokenized into a list of individual tokens using the
   * {@link #tokenize(String formula)} method. The tokens are then processed one by one in a loop.
   * If the current token is a cell reference (e.g. R1C1), its value is obtained
   * using the {@link #getCellValue(String cellRef)} method and pushed onto the values stack.
   * If the current token is an integer or fractional number, it is parsed into a
   * double value and pushed onto the values stack.
   * If the current token is an operator, its precedence is checked against the top of
   * the operator stack using the {@link #hasPrecedence(char op1, char op2)} method. If the current operator has higher precedence,
   * it is pushed onto the operator stack. If the current operator has lower or equal precedence,
   * the top operator is popped from the operator stack, and two operands are popped from the values stack.
   * The two operands are then operated on using the popped operator, and the result is pushed back onto the values stack.
   * The loop continues until all tokens have been processed. Once the loop is finished, any remaining
   * operators on the operator stack are processed in a similar fashion.
   * Finally, the result is obtained by popping the last remaining value from the values stack.
   * If the formula was properly formatted, this value should represent the result of the calculation.
   * If any errors occur during the evaluation of the formula, a FormulaException is thrown.
   * <p>
   * Изчислява дадената формула и връща резултата.
   * Приема формулата като вход и връща резултата от изчислението.
   * Алгоритъмът използва базиран на стек подход за анализиране на формулата, където операндите и
   * операторите се избутват и изваждат от два отделни стека.
   * Формулата за въвеждане първо се токенизира в списък от отделни токени, като се използва
   * метод {@link #tokenize(String formula)}. След това токените се обработват един по един в цикъл.
   * Ако текущият токен е препратка към клетка (напр. R1C1), се получава неговата стойност
   * с помощта на метода {@link #getCellValue(String cellRef)} и поставен в стека със стойности.
   * Ако текущият токен е цяло число или дробно число, той се превръща в
   * двойна стойност и избутан в стека със стойности.
   * Ако текущият токен е оператор, неговият приоритет се проверява спрямо началото на
   * стека на оператора с помощта на метода {@link #hasPrecedence(char op1, char op2)}.
   * Ако текущият оператор има по-висок приоритет,
   * избутва се в стека на оператора. Ако текущият оператор има по-нисък или равен приоритет,
   * горният оператор се изважда от стека на операторите, а два операнда се изваждат от стека със стойностите.
   * След това двата операнда се оперират с помощта на оператора popped и резултатът се връща обратно в стека със стойности.
   * Цикълът продължава, докато всички токени бъдат обработени. След като цикълът приключи, всички останали
   * операторите в операторния стек се обработват по подобен начин.
   * Накрая, резултатът се получава чрез изваждане на последната останала стойност от стека със стойности.
   * Ако формулата е правилно форматирана, тази стойност трябва да представлява резултата от изчислението.
   * Ако възникнат грешки по време на изчисляване на формулата, се хвърля FormulaException.
   *
   * @param formula The formula to evaluate.
   *                <p>
   *                Формулата за изчисляване.
   * @return The result of the formula.
   * <p>
   * Резултатът от формулата.
   * @throws ArithmeticException      If the formula contains division by zero.
   *                                  <p>
   *                                  Ако формулата съдържа деление на нула.
   * @throws IllegalArgumentException If the formula contains an invalid operator.
   *                                  <p>
   *                                  Ако формулата съдържа невалиден оператор.
   */
  public double evaluate(String formula) throws ArithmeticException {

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
   * The tokens are individual elements of the formula that can be evaluated separately,
   * such as cell references, numbers, and operators.
   * The algorithm iterates through each character of the input formula, starting at the first character.
   * If the current character is a digit or a period (.), it is added to a StringBuilder object,
   * which is used to construct the current token.
   * If the current character is an alphabetic letter, it is also added to the StringBuilder object.
   * If the current character is not a digit, period, or alphabetic letter,
   * it means that a new token is starting. In this case, the algorithm checks whether the StringBuilder
   * object has any characters. If it does, it means that a token has just ended, so the StringBuilder
   * is converted to a string and added to the list of tokens. Then, the StringBuilder is cleared,
   * ready to construct the next token. Finally, the current character is added to the list of tokens.
   * After iterating through all the characters in the input formula, there may be a final token left in
   * the StringBuilder object. If this is the case, the StringBuilder is converted to a string and added to the list of tokens.
   * Finally, the algorithm checks if the first token in the list is an equals sign (=).
   * If it is, the equals sign is removed from the list of tokens because it is not needed for evaluation.
   * The end result is a list of tokens that represents the input formula, ready to be evaluated.
   * <p>
   * Токенизира даден низ на формула в списък с токени.
   * Токените са отделни елементи от формулата, които могат да бъдат оценени отделно,
   * като препратки към клетки, числа и оператори.
   * Алгоритъмът преминава през всеки знак от формулата за въвеждане, започвайки от първия знак.
   * Ако текущият знак е цифра или точка (.), той се добавя към StringBuilder обект,
   * който се използва за конструиране на текущия токен.
   * Ако текущият символ е буква, той също се добавя към обекта StringBuilder.
   * Ако текущият знак не е цифра, точка или буква,
   * това означава, че започва нов токен. В този случай алгоритъмът проверява дали StringBuilder
   * обектът има някакви знаци. Ако има, това означава, че токенът току-що е приключил, така че StringBuilder
   * се преобразува в низ и се добавя към списъка с токени. След това StringBuilder се изчиства,
   * готов за изграждане на следващия токен. Накрая, текущият символ се добавя към списъка с токени.
   * След повторение на всички знаци във формулата за въвеждане може да остане последен токен в
   * обектът StringBuilder. Ако случаят е такъв, StringBuilder се преобразува в низ и се добавя към списъка с токени.
   * Накрая, алгоритъмът проверява дали първият токен в списъка е знак за равенство (=).
   * Ако е така, знакът за равенство се премахва от списъка с токени, защото не е необходим за изчисления.
   * Крайният резултат е списък с токени, който представлява формулата за въвеждане, готова за изчисления.
   *
   * @param formula The formula string to tokenize.
   *                <p>
   *                Низът на формулата за токенизиране.
   * @return A list of tokens extracted from the formula string.
   * <p>
   * Списък с токени, извлечени от низа на формулата.
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
        if (currentToken.length() > 0) {
          tokens.add(currentToken.toString());
          currentToken.setLength(0);
        }
        tokens.add(Character.toString(currentChar));
      }
    }

    if (currentToken.length() > 0) {
      tokens.add(currentToken.toString());
    }

    if (tokens.size() > 0 && tokens.get(0).equals("=")) {
      tokens.remove(0);
    }

    return tokens;
  }

  /**
   * Checks if a given token is a valid cell reference.
   * <p>
   * Проверява дали даден токен е валидна препратка към клетка.
   *
   * @param token The token to check
   *              <p>
   *              Токен за проверка
   * @return true if the token is a valid cell reference, false otherwise
   * <p>
   * true, ако токенът е валидна препратка към клетка, false в противен случай
   */
  private boolean isCellReference(String token) {
    return cellRefPattern.matcher(token).matches();
  }

  /**
   * Returns the numerical value of a given cell reference in the table.
   * Recursion is used to calculate the cell value if it is another formula.
   * <p>
   * Връща числовата стойност на дадена препратка към клетка в таблицата.
   * Използва се рекурсия, за да се изчисли стойността на клетката ако е формула.
   *
   * @param cellRef the reference to the cell, in the format "RxCy", where x is the row number and y is the column number
   *                <p>
   *                препратката към клетката във формат "RxCy", където x е номерът на реда, а y е номерът на колоната
   * @return the numerical value of the cell, or 0 if the cell value is not a valid number
   * <p>
   * числовата стойност на клетката или 0, ако стойността на клетката не е валидно число
   */
  private double getCellValue(String cellRef) {

    String[] parts = cellRef.split(ROW_OR_COLUMN_PATTERN);
    int row = Integer.parseInt(parts[1]) - 1;
    int col = Integer.parseInt(parts[2]) - 1;

    TableCell cell;
    try {
      cell = TableRepository.getInstance().getCell(row, col);
    } catch (IllegalArgumentException e) {
      return 0;
    }

    if (cell.getType() == CellType.INTEGER || cell.getType() == CellType.FRACTIONAL) {
      return Double.parseDouble(cell.getValueAsString());
    }

    if (cell.getType() == CellType.STRING) {
      if (cell.getValueAsString().matches(INTEGER_OR_FRACTIONAL_NUMBER_PATTERN)) {
        return Double.parseDouble(cell.getValueAsString());
      } else {
        return 0.0;
      }
    }

    if (cell.getType() == CellType.EMPTY) {
      return 0.0;
    }

    if (cell.getType() == CellType.FORMULA) {
      return this.evaluate(cell.getValueAsString());
    }

    return 0;
  }

  /**
   * Determines if the given token is an arithmetic operator.
   * <p>
   * Определя дали подаения токен е аритметичен оператор.
   *
   * @param token the token to be checked
   *              <p>
   *              токенът за проверка
   * @return true if the token is an operator (+, -, *, /, or ^); false otherwise
   * <p>
   * вярно, ако токенът е оператор (+, -, *, / или ^); невярно в противен случай
   */
  private boolean isOperator(String token) {
    return token.length() == 1 && MATH_ALLOWED_OPERATORS.contains(token);
  }

  /**
   * Determines whether the first operator has higher precedence than the second operator.
   * Precedence rules:
   * Exponentiation (^) has the highest precedence
   * Multiplication (*) and division (/) have higher precedence than addition (+) and subtraction (-)
   * <p>
   * Определя дали първият оператор има по-висок приоритет от втория оператор.
   * Правила за приоритет:
   * Степенуването (^) има най-висок приоритет
   * Умножението (*) и делението (/) имат по-висок приоритет от събирането (+) и изваждането (-)
   *
   * @param op1 the first operator
   *            <p>
   *            първият оператор
   * @param op2 the second operator
   *            <p>
   *            вторият оператор
   * @return true if the first operator has higher precedence than the second operator, false otherwise
   * <p>
   * вярно, ако първият оператор има по-висок приоритет от втория оператор, невярно в противен случай
   */
  private boolean hasPrecedence(char op1, char op2) {

    if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
      return true;
    }

    return (op1 == '^') && (op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-');
  }

  /**
   * Applies the given operation to the two operands.
   * <p>
   * Прилага дадената операция към двата операнда.
   *
   * @param op the operator to be applied
   *           <p>
   *           операторът, който ще се прилага
   * @param b  the second operand
   *           <p>
   *           вторият оператор
   * @param a  the first operand
   *           <p>
   *           първият оператор
   * @return the result of the operation
   * <p>
   * резултатът от операцията
   * @throws ArithmeticException      if division by zero is attempted
   *                                  <p>
   *                                  ако се направи опит за деление на нула
   * @throws IllegalArgumentException if an invalid operator is provided
   *                                  <p>
   *                                  ако е предоставен невалиден оператор
   */
  private double applyOperation(char op, double b, double a) throws ArithmeticException {

    switch (op) {
      case '+':
        return a + b;
      case '-':
        return a - b;
      case '*':
        return a * b;
      case '/':
        if (b == 0) {
          throw new ArithmeticException(DIVISION_BY_ZERO_ERROR_MESSAGE);
        }
        return a / b;
      case '^':
        return Math.pow(a, b);
      default:
        throw new IllegalArgumentException(String.format(INVALID_OPERATOR_MESSAGE, op));
    }
  }
}
