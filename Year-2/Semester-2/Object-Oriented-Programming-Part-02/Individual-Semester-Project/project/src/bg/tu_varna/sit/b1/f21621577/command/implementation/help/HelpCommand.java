package bg.tu_varna.sit.b1.f21621577.command.implementation.help;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;

/**
 * Implementation of the {@link Command} interface that displays a list of available commands.
 * <p>
 * Реализация на интерфейса {@link Command}, която показва списък с налични команди.
 */
public class HelpCommand implements Command {

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

    return "The following commands are supported:\n" +
            "open <file>                    opens <file>\n" +
            "close                          closes currently opened file\n" +
            "save                           saves the currently open file\n" +
            "saveas <file>                  saves the currently open file in <file>\n" +
            "print                          prints the table formatted to the console\n" +
            "edit <row> <col> <new value>   edits the value at the given indexes with the new one\n" +
            "help                           prints this information\n" +
            "exit                           exists the program\n"
                    .trim();
  }
}