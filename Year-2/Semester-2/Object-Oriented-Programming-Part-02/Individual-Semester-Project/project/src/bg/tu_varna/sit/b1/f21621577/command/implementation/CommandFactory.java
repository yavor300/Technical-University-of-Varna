package bg.tu_varna.sit.b1.f21621577.command.implementation;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory class for creating commands based on the provided abstract factory.
 * <p>
 * Клас-фабрика за създаване на команди, базирани на предоставената абстрактна фабрика.
 */
public class CommandFactory {

  /**
   * Returns a new command instance created by the provided abstract factory.
   * <p>
   * Връща нова инстанция на команда, създадена от предоставената абстрактна фабрика.
   *
   * @param factory the abstract factory to use for creating the command
   *                <p>
   *                абстрактната фабрика, която да се използва за създаване на командата
   * @return the newly created command instance
   * <p>
   * новосъздадената команда
   */
  public static Command getCommand(CommandAbstractFactory factory) {
    return factory.createCommand();
  }
}
