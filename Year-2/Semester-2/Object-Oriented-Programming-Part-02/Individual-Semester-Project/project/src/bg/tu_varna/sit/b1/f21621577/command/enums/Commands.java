package bg.tu_varna.sit.b1.f21621577.command.enums;

/**
 * An enumeration representing the available commands in the application.
 * <p>
 * Енумерация, представяща наличните команди в приложението.
 */
public enum Commands {

  OPEN("open"),
  PRINT("print"),
  EDIT("edit"),
  CLOSE("close"),
  SAVE("save"),
  SAVEAS("saveas"),
  HELP("help"),
  EXIT("exit");

  private final String value;

  /**
   * Constructs a Commands object with the specified value.
   * <p>
   * Конструира обект Commands със зададената стойност.
   *
   * @param value the string value of the command
   *              <p>
   *              стойността на низа на командата
   */
  Commands(String value) {
    this.value = value;
  }

  /**
   * Returns the string value of the command.
   * <p>
   * Връща низовата стойност на командата.
   *
   * @return the string value of the command
   * <p>
   * низовата стойност на командата.
   */
  public String getValue() {
    return value;
  }
}
