package bg.tu_varna.sit.b1.f21621577.command.implementation.save;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

/**
 * A factory for creating an SaveCommand object with a list of arguments.
 */
public class SaveCommandFactory implements CommandAbstractFactory {

  /**
   * Creates a new SaveCommand object.
   *
   * @return the new SaveCommand object
   */
  @Override
  public Command createCommand() {
    return new SaveCommand();
  }

}