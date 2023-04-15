package bg.tu_varna.sit.b1.f21621577.command.base;

import java.io.IOException;

/**
 * The Command interface defines the behavior of a command that can be executed by the application.
 */
public interface Command {

  /**
   * Executes the command.
   *
   * @return a message indicating the result of the command execution
   * @throws IOException if an I/O error occurs during command execution
   */
  String execute() throws IOException;

}
