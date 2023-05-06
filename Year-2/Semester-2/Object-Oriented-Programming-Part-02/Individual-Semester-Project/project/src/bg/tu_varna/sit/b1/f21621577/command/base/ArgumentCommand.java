package bg.tu_varna.sit.b1.f21621577.command.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_TABLE_FILENAME;

/**
 * An abstract base class for commands that take a list of string arguments. Subclasses should implement the
 * {@link Command} interface and define how to execute the command logic.
 * This class provides a default constructor that initializes the arguments with a default value, and a constructor
 * that allows passing a custom list of arguments. It also provides a method to retrieve the current list of arguments.
 * Subclasses can add additional fields, constructors, and methods as needed to implement their specific behavior.
 * <p>
 * Абстрактен базов клас за команди, които приемат списък от низови аргументи. Подкласовете трябва да имплементират
 * интерфейс {@link Command} и дефинират как да се изпълни командната логика.
 * Този клас предоставя конструктор по подразбиране, който инициализира аргументите със стойност по подразбиране, и конструктор
 * което позволява предаване на потребителски списък с аргументи. Той също така предоставя метод за извличане на текущия списък с аргументи.
 * Подкласовете могат да добавят допълнителни полета, конструктори и методи, ако е необходимо, за да реализират специфичното си поведение.
 */
public abstract class ArgumentCommand implements Command {

  /**
   * The list of arguments to be used by this command.
   * <p>
   * Списъкът с аргументи, които да се използват от тази команда.
   */
  private final List<String> arguments;

  /**
   * Constructs a new ArgumentCommand object with the default table filename as the only argument.
   * <p>
   * Конструира нов обект ArgumentCommand с името на файла на таблицата по подразбиране като единствен аргумент.
   */
  protected ArgumentCommand() {
    this.arguments = new ArrayList<>(Collections.singletonList(DEFAULT_TABLE_FILENAME));
  }

  /**
   * Constructs a new ArgumentCommand object with the specified array of arguments.
   * <p>
   * Конструира нов обект ArgumentCommand с посочения масив от аргументи.
   *
   * @param arguments the arguments for the command
   *                  <p>
   *                  аргументите за командата
   */
  protected ArgumentCommand(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Returns the arguments for the command.
   * <p>
   * Връща аргументите за командата.
   *
   * @return the arguments for the command as a list of strings
   * <p>
   * аргументите за командата като списък от низове
   */
  protected List<String> getArguments() {
    return arguments;
  }
}
