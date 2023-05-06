package bg.tu_varna.sit.b1.f21621577.command.base;

import java.io.IOException;

/**
 * The Command interface defines the behavior of a command that can be executed by the application.
 * <p>
 * Командният интерфейс определя поведението на команда, която може да бъде изпълнена от приложението.
 */
public interface Command {

  /**
   * Executes the command.
   * <p>
   * Изпълнява командата.
   *
   * @return a message indicating the result of the command execution
   * <p>
   * съобщение, показващо резултата от изпълнението на командата
   * @throws IOException if an I/O error occurs during command execution
   *                     <p>
   *                     ако възникне I/O грешка по време на изпълнение на командата
   */
  String execute() throws IOException;

}
