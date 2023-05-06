package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory for creating an PrintCommand object with a list of arguments.
 * <p>
 * Клас-фабрика за създаване на PrintCommand обект със списък от аргументи.
 */
public class PrintCommandFactory implements CommandAbstractFactory {

  /**
   * Creates a new PrintCommand object.
   * <p>
   * Създава нов обект PrintCommand.
   *
   * @return the new PrintCommand object
   * <p>
   * новия обект PrintCommand
   */
  @Override
  public Command createCommand() {
    return new PrintCommand();
  }
}