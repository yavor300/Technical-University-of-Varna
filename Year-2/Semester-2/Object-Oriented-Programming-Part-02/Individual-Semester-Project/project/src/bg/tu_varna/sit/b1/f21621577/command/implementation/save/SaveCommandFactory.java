package bg.tu_varna.sit.b1.f21621577.command.implementation.save;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory for creating an SaveCommand object without a list of arguments.
 * <p>
 * Клас-фабрика за създаване на обект SaveCommand без списък от аргументи.
 */
public class SaveCommandFactory implements CommandAbstractFactory {

  /**
   * Creates a new SaveCommand object.
   * <p>
   * Създава нов обект SaveCommand.
   *
   * @return the new SaveCommand object
   * <p>
   * новия обект SaveCommand
   */
  @Override
  public Command createCommand() {
    return new SaveCommand();
  }

}