package bg.tu_varna.sit.b1.f21621577.command.base;

import java.io.IOException;

/**
 * The Command interface represents a single command that can be executed by the application.
 * All concrete command classes must implement this interface.
 */
public interface Command {

  /**
   * Executes the command.
   *
   * @return {@code true} if the command was executed successfully; {@code false} otherwise
   * @throws IOException if an I/O error occurs during command execution
   */
  boolean execute() throws IOException;

}
