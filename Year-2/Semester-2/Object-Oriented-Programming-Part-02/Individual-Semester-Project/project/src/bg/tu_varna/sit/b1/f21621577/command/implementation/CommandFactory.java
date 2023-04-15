package bg.tu_varna.sit.b1.f21621577.command.implementation;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory class for creating commands based on the provided abstract factory.
 */
public class CommandFactory {

  /**
   * Returns a new command instance created by the provided abstract factory.
   *
   * @param factory the abstract factory to use for creating the command
   * @return the newly created command instance
   */
  public static Command getCommand(CommandAbstractFactory factory) {
    return factory.createCommand();
  }
}
