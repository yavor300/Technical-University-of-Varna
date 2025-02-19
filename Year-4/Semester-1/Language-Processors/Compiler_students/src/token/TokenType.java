package token;

import java.util.HashSet;
import java.util.Set;

public enum TokenType {

  INT("int"), CHAR("char"), BOOLEAN("boolean"), VOID("void"),
  WHILE("while"), IF("if"), ELSE("else"), RETURN("return"), LENGTH("length"),
  PROGRAM("program"), PRINT("print"), READ("read"), TRUE("true"), FALSE("false"),

  LSQUARE("["), RSQUARE("]"), LBRACKET("{"), RBRACKET("}"),
  LPAREN("("), RPAREN(")"), COMMA(","), SEMICOLON(";"),
  SINGLE_QUOTE("'"), DOUBLE_QUOTES("\""), AT("@"), ARROW("->"),

  PLUS("+"), MINUS("-"),

  MUL("*"), DIV("/"), MOD("%"),

  AND("&&"), OR("||"),

  BECOMES("="),

  EQUALS("=="), NOTEQUALS("!="), NOT("!"),
  GREATER(">"), LESS("<"),
  GREATER_EQ(">="), LESS_EQ("<="),

  IDENTIFIER("identifier"), NUMBER("number"), CHAR_LITERAL("character literal"), STRING_LITERAL("string literal"),

  OTHER("");

  public final String value;

  TokenType(final String value) {
    this.value = value;
  }

  private static final Set<String> keywords = new HashSet<>();

  static {
    keywords.add(IF.value);
    keywords.add(INT.value);
    keywords.add(CHAR.value);
    keywords.add(ELSE.value);
    keywords.add(READ.value);
    keywords.add(TRUE.value);
    keywords.add(VOID.value);
    keywords.add(FALSE.value);
    keywords.add(WHILE.value);
    keywords.add(PRINT.value);
    keywords.add(LENGTH.value);
    keywords.add(RETURN.value);
    keywords.add(BOOLEAN.value);
    keywords.add(PROGRAM.value);
  }

  public static boolean isKeyword(String keyword) {
    return keywords.contains(keyword);
  }

  private static final Set<TokenType> characterLiteral = new HashSet<>();

  static {
    characterLiteral.add(CHAR_LITERAL);
    characterLiteral.add(STRING_LITERAL);
  }

  public static boolean isCharacterLiteral(TokenType tokenType) {
    return characterLiteral.contains(tokenType);
  }

  private static final Set<TokenType> primitiveType = new HashSet<>();

  static {
    primitiveType.add(INT);
    primitiveType.add(CHAR);
    primitiveType.add(BOOLEAN);
  }

  public static boolean isPrimitiveType(TokenType tokenType) {
    return primitiveType.contains(tokenType);
  }

  private static final Set<TokenType> unaryOperators = new HashSet<>();

  static {
    unaryOperators.add(NOT);
    unaryOperators.add(MINUS);
  }

  public static boolean isUnaryOperator(TokenType tokenType) {
    return unaryOperators.contains(tokenType);
  }

  private static final Set<TokenType> relationalOperators = new HashSet<>();

  static {
    relationalOperators.add(EQUALS);
    relationalOperators.add(NOTEQUALS);
    relationalOperators.add(GREATER);
    relationalOperators.add(GREATER_EQ);
    relationalOperators.add(LESS);
    relationalOperators.add(LESS_EQ);
  }

  public static boolean isRelationalOperator(TokenType tokenType) {
    return relationalOperators.contains(tokenType);
  }

  private static final Set<TokenType> operatorGroupOne = new HashSet<>();

  static {
    operatorGroupOne.add(PLUS);
    operatorGroupOne.add(MINUS);
    operatorGroupOne.add(OR);
  }

  public static boolean isOperatorGroupOne(TokenType tokenType) {
    return operatorGroupOne.contains(tokenType);
  }

  private static final Set<TokenType> operatorGroupTwo = new HashSet<>();

  static {
    operatorGroupTwo.add(MUL);
    operatorGroupTwo.add(DIV);
    operatorGroupTwo.add(MOD);
    operatorGroupTwo.add(AND);
  }

  public static boolean isOperatorGroupTwo(TokenType tokenType) {
    return operatorGroupTwo.contains(tokenType);
  }

  private static final Set<TokenType> simpleStatementTerminal = new HashSet<>();

  static {
    simpleStatementTerminal.add(IDENTIFIER);
    simpleStatementTerminal.add(AT);
    simpleStatementTerminal.add(RETURN);
    simpleStatementTerminal.add(PRINT);
    simpleStatementTerminal.add(READ);
  }

  public static boolean isSimpleStatementTerminal(TokenType tokenType) {
    return simpleStatementTerminal.contains(tokenType) || primitiveType.contains(tokenType);
  }

  private static final Set<TokenType> compoundStatementTerminal = new HashSet<>();

  static {
    compoundStatementTerminal.add(IF);
    compoundStatementTerminal.add(WHILE);
  }

  public static boolean isCompoundStatementTerminal(TokenType tokenType) {
    return compoundStatementTerminal.contains(tokenType);
  }

  public static boolean isStatementTerminal(TokenType tokenType) {
    return isSimpleStatementTerminal(tokenType) ||
            isCompoundStatementTerminal(tokenType);
  }

  private static final Set<TokenType> factorTerminal = new HashSet<>();

  static {
    factorTerminal.add(IDENTIFIER);
    factorTerminal.add(NUMBER);
    factorTerminal.add(LPAREN);
    factorTerminal.add(AT);
    factorTerminal.add(LENGTH);
  }

  public static boolean isFactorTerminal(TokenType tokenType) {
    return factorTerminal.contains(tokenType);
  }

  public static boolean isLiteralTerminal(TokenType tokenType) {
    return isFactorTerminal(tokenType) || isPrimitiveType(tokenType) ||
            tokenType == STRING_LITERAL || tokenType == CHAR_LITERAL;
  }
}
