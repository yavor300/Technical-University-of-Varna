package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_TABLE_FILENAME;

/**
 * A factory for creating an OpenCommand object with a list of arguments.
 * <p>
 * Клас-фабрика за създаване на OpenCommand обект със списък от аргументи.
 */
public class OpenCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the OpenCommand.
   * <p>
   * Списъкът с аргументи, които трябва да бъдат предадени на OpenCommand.
   */
  private List<String> arguments;

  /**
   * Constructs a new OpenCommand object with a list of arguments.
   * <p>
   * Конструира нов OpenCommand обект със списък от аргументи.
   *
   * @param arguments the list of arguments to be passed to the OpenCommand
   *                  <p>
   *                  списъкът с аргументи, които трябва да бъдат предадени на OpenCommand
   */
  public OpenCommandFactory(List<String> arguments) {
    setArguments(arguments);
  }

  /**
   * Creates a new OpenCommand object with the list of arguments.
   * <p>
   * Създава нов OpenCommand обект със списъка с аргументи.
   *
   * @return the new OpenCommand object
   */
  @Override
  public Command createCommand() {
    return new OpenCommand(arguments);
  }

  /**
   * Sets the list of arguments for the command. If the argument list is null or empty,
   * the default table filename will be used. Otherwise, the provided argument list will be used.
   * <p>
   * Задава списъка с аргументи за командата. Ако списъкът с аргументи е нулев или празен,
   * ще се използва името на файла на таблицата по подразбиране. В противен случай ще се използва предоставеният списък с аргументи
   *
   * @param arguments the list of arguments to set for the command, or null/empty to use the default filename
   *                  <p>
   *                  списъкът с аргументи, които да зададете за командата, или null/empty, за да използвате името на файла по подразбиране
   */
  public void setArguments(List<String> arguments) {

    if (arguments == null || arguments.size() == 0) {
      this.arguments = new ArrayList<>(Collections.singletonList(DEFAULT_TABLE_FILENAME));
    } else {
      this.arguments = arguments;
    }
  }
}