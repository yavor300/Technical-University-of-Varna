package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

import java.util.List;

/**
 * A factory for creating an OpenFileCommand object with a list of arguments.
 */
public class OpenCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the OpenFileCommand.
   */
  private final List<String> arguments;

  /**
   * Constructs a new OpenCommandFactory object with a list of arguments.
   *
   * @param arguments the list of arguments to be passed to the OpenFileCommand
   */
  public OpenCommandFactory(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Creates a new OpenFileCommand object with the list of arguments.
   *
   * @return the new OpenFileCommand object
   */
  @Override
  public Command createCommand() {
    return new OpenFileCommand(arguments);
  }
}