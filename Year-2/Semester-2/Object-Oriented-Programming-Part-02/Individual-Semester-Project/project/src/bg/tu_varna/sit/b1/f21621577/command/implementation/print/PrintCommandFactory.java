package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory for creating an PrintFileCommand object with a list of arguments.
 */
public class PrintCommandFactory implements CommandAbstractFactory {

  /**
   * Creates a new PrintFileCommand object with the list of arguments.
   *
   * @return the new PrintFileCommand object
   */
  @Override
  public Command createCommand() {
    return new PrintFileCommand();
  }
}