package bg.tu_varna.sit.b1.f21621577.command.implementation.saveas;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.save.SaveCommand;

import java.util.List;

/**
 * A factory for creating an SaveCommand object with a list of arguments.
 */
public class SaveAsCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the SaveCommand.
   */
  private final List<String> arguments;

  /**
   * Constructs a new SaveCommand object with a list of arguments.
   *
   * @param arguments the list of arguments to be passed to the SaveCommand
   */
  public SaveAsCommandFactory(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Creates a new SaveCommand object with the list of arguments.
   *
   * @return the new SaveCommand object
   */
  @Override
  public Command createCommand() {
    return new SaveCommand(arguments);
  }
}