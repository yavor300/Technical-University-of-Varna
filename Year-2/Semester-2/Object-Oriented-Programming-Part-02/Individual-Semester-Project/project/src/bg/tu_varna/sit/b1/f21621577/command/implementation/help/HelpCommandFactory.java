package bg.tu_varna.sit.b1.f21621577.command.implementation.help;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory for creating a HelpCommand object with a list of arguments.
 */
public class HelpCommandFactory implements CommandAbstractFactory {

  /**
   * Creates a new HelpCommand object.
   *
   * @return the new HelpCommand object
   */
  @Override
  public Command createCommand() {
    return new HelpCommand();
  }
}