package bg.tu_varna.sit.b1.f21621577.command.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_TABLE_FILENAME;

public abstract class ArgumentCommand implements Command {

  private final List<String> arguments;

  protected ArgumentCommand() {
    this.arguments = new ArrayList<>(Collections.singletonList(DEFAULT_TABLE_FILENAME));
  }

  /**
   * Constructs a new ArgumentCommand object with the specified array of arguments.
   *
   * @param arguments the arguments for the command
   */
  protected ArgumentCommand(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Returns the arguments for the command.
   *
   * @return the arguments for the command as a list of strings
   */
  protected List<String> getArguments() {
    return arguments;
  }
}
