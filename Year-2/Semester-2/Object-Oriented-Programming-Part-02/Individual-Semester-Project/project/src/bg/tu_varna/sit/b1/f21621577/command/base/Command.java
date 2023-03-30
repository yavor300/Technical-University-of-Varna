package bg.tu_varna.sit.b1.f21621577.command.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_TABLE_FILENAME;

/**
 * The abstract base class for all command classes.
 * Commands are used to encapsulate functionality that can be executed on a system.
 * This class defines a constructor that accepts a list of strings representing the command arguments,
 * an abstract method "execute" which must be implemented by concrete command classes, and a method to
 * retrieve the command arguments.
 */
public abstract class Command {

  private final List<String> arguments;

  public Command() {
    this.arguments = new ArrayList<>(Collections.singletonList(DEFAULT_TABLE_FILENAME));
  }

  /**
   * Constructs a new Command object with the specified array of arguments.
   *
   * @param arguments the arguments for the command
   */
  public Command(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Executes the command.
   * This method must be implemented by concrete command classes.
   *
   * @return true if the command was executed successfully; false otherwise
   * @throws IOException if an I/O error occurs during command execution
   */
  public abstract boolean execute() throws IOException;

  /**
   * Returns the arguments for the command.
   *
   * @return the arguments for the command as a list of strings
   */
  public List<String> getArguments() {
    return arguments;
  }
}
