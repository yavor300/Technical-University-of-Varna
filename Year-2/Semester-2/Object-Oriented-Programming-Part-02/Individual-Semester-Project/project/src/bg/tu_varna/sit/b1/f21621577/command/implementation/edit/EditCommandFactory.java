package bg.tu_varna.sit.b1.f21621577.command.implementation.edit;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

import java.util.List;

/**
 * A factory for creating an EditCommand object with a list of arguments.
 * <p>
 * Фабричен клас за създаване на обект EditCommand със списък от аргументи.
 */
public class EditCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the EditCommand.
   * <p>
   * Списъкът с аргументи, които трябва да бъдат предадени на EditCommand.
   */
  private final List<String> arguments;

  /**
   * Constructs a new EditCommandFactory object with a list of arguments.
   * <p>
   * Конструира нов обект EditCommandFactory със списък от аргументи.
   *
   * @param arguments the list of arguments to be passed to the EditCommand
   *                  <p>
   *                  списъкът с аргументи, които да бъдат предадени на EditCommand
   */
  public EditCommandFactory(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Creates a new EditCommand object with the list of arguments.
   * <p>
   * Създава нов обект EditCommand със списъка с аргументи.
   *
   * @return the new EditCommand object
   * <p>
   * новия обект EditCommand
   */
  @Override
  public Command createCommand() {
    return new EditCommand(arguments);
  }
}
