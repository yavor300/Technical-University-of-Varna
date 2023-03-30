package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_TABLE_FILENAME;

/**
 * A factory for creating an OpenFileCommand object with a list of arguments.
 */
public class OpenCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the OpenFileCommand.
   */
  private List<String> arguments;

  /**
   * Constructs a new OpenCommandFactory object with a list of arguments.
   *
   * @param arguments the list of arguments to be passed to the OpenFileCommand
   */
  public OpenCommandFactory(List<String> arguments) {
    setArguments(arguments);
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

  /**
   * Sets the list of arguments for the command. If the argument list is null or empty,
   * the default table filename will be used. Otherwise, the provided argument list will be used.
   *
   * @param arguments the list of arguments to set for the command, or null/empty to use the default filename
   */
  public void setArguments(List<String> arguments) {

    if (arguments == null || arguments.size() == 0) {
      this.arguments = new ArrayList<>(Collections.singletonList(DEFAULT_TABLE_FILENAME));
    } else {
      this.arguments = arguments;
    }
  }
}