package bg.tu_varna.sit.b1.f21621577.command.implementation.help;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.HELP_COMMAND_MESSAGE;

/**
 * Implementation of the {@link Command} interface that displays a list of available commands.
 * <p>
 * Реализация на интерфейса {@link Command}, която показва списък с налични команди.
 */
public class HelpCommand implements Command {

  /**
   * Package-private constructor for creating a {@code HelpCommand} object.
   * <p>
   * Пакетно частен конструктор за създаване на обект {@code HelpCommand}.
   */
  HelpCommand() {
  }

  /**
   * Executes the command to display a list of available commands.
   * <p>
   * Изпълнява командата, за да покаже списък с налични команди.
   *
   * @return a message indicating the available commands
   * съобщение, указващо наличните команди
   */
  @Override
  public String execute() {

    return HELP_COMMAND_MESSAGE.trim();
  }
}