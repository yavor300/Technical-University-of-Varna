package bg.tu_varna.sit.b1.f21621577.command.implementation.close;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory for creating an CloseCommand object with a list of arguments.
 * <p>
 * Фабричен клас за създаване на обект CloseCommand със списък от аргументи.
 */
public class CloseCommandFactory implements CommandAbstractFactory {

  /**
   * Creates a new CloseCommand object.
   * <p>
   * Създава нов обект CloseCommand.
   *
   * @return the new CloseCommand object
   * <p>
   * новия обект CloseCommand
   */
  @Override
  public Command createCommand() {
    return new CloseCommand();
  }
}
