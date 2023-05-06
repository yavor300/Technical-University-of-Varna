package bg.tu_varna.sit.b1.f21621577.command.implementation.saveas;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.save.SaveCommand;

import java.util.List;

/**
 * A factory for creating an SaveCommand object with a list of arguments.
 * <p>
 * Клас-фабрика за създаване на обект SaveCommand със списък от аргументи.
 */
public class SaveAsCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the SaveCommand.
   * <p>
   * Списъкът с аргументи, които трябва да бъдат предадени на SaveCommand.
   */
  private final List<String> arguments;

  /**
   * Constructs a new SaveCommand object with a list of arguments.
   * <p>
   * Конструира нов обект SaveCommand със списък от аргументи.
   *
   * @param arguments the list of arguments to be passed to the SaveCommand
   *                  <p>
   *                  списъкът с аргументи, които да бъдат предадени на SaveCommand
   */
  public SaveAsCommandFactory(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Creates a new SaveCommand object with the list of arguments.
   * <p>
   * Създава нов обект SaveCommand със списъка с аргументи.
   *
   * @return the new SaveCommand object
   * <p>
   * новия обект SaveCommand
   */
  @Override
  public Command createCommand() {
    return new SaveCommand(arguments);
  }
}
