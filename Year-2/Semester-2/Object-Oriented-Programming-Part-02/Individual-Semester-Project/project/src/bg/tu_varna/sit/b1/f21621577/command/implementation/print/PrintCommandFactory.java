package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory for creating an PrintCommand object with a list of arguments.
 */
public class PrintCommandFactory implements CommandAbstractFactory {

  /**
   * Creates a new PrintCommand object.
   *
   * @return the new PrintCommand object
   */
  @Override
  public Command createCommand() {
    return new PrintCommand();
  }
}