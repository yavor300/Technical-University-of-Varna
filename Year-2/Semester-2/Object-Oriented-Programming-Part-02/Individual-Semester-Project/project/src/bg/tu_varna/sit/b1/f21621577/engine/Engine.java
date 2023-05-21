package bg.tu_varna.sit.b1.f21621577.engine;

import bg.tu_varna.sit.b1.f21621577.command.enums.Commands;
import bg.tu_varna.sit.b1.f21621577.command.implementation.CommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.close.CloseCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.edit.EditCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.help.HelpCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.open.OpenCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.print.PrintCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.save.SaveCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.saveas.SaveAsCommandFactory;

import static bg.tu_varna.sit.b1.f21621577.constants.Messages.COMMAND_NOT_FOUND_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.HELP_COMMAND_MESSAGE;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.DO_NOT_SPLIT_IF_ENCLOSED_IN_QUOTES_PATTERN;

/**
 * The Engine class is responsible for starting and running the application.
 * It reads user input from the console, processes the commands, and executes them using the CommandExecutor.
 * <p>
 * Класът Engine отговаря за стартирането и изпълнението на приложението.
 * Той чете въведените от потребителя данни от конзолата, обработва командите и ги изпълнява с помощта на CommandExecutor.
 */
public class Engine {

  /**
   * The CommandExecutor instance responsible for executing commands.
   * <p>
   * Инстанция на CommandExecutor, отговорен за изпълнението на команди.
   */
  private final CommandExecutor commandExecutor = new CommandExecutor();

  /**
   * Starts the application and enters the command loop.
   * Reads user input, processes the commands, and executes them.
   * <p>
   * Стартира приложението и влиза в командния цикъл.
   * Чете въведеното от потребителя, обработва командите и ги изпълнява.
   */
  public void start() {

    printStartupHeader();

    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        List<String> input = new ArrayList<>(Arrays.asList(scanner.nextLine().trim().split(DO_NOT_SPLIT_IF_ENCLOSED_IN_QUOTES_PATTERN)));
        String menuChoice = input.get(0);

        if (menuChoice.trim().isEmpty()) continue;

        Commands command;
        try {
          command = Commands.valueOf(menuChoice.toUpperCase());
        } catch (IllegalArgumentException e) {
          System.out.printf(COMMAND_NOT_FOUND_MESSAGE + "%n", menuChoice);
          continue;
        }

        commandExecutor.executeCommand(command, input.subList(1, input.size()));
      }
    }
  }

  /**
   * Prints the header for the CLI application.
   * The header consists of an ASCII art logo and additional information.
   * <p>
   * Отпечатва заглавието за CLI приложението.
   * Заглавието се състои от ASCII арт лого и допълнителна информация.
   */
  private void printStartupHeader() {

    System.out.println("  _____  _    ____  _     _____       _    ____  ____  \n" +
            " |_   _|/ \\  | __ )| |   | ____|     / \\  |  _ \\|  _ \\ \n" +
            "   | | / _ \\ |  _ \\| |   |  _|      / _ \\ | |_) | |_) |\n" +
            "   | |/ ___ \\| |_) | |___| |___    / ___ \\|  __/|  __/ \n" +
            "   |_/_/   \\_\\____/|_____|_____|  /_/   \\_\\_|   |_|    \n" +
            "                                                       ");
    System.out.println("Author: Yavor Chamov\n");
    System.out.println(HELP_COMMAND_MESSAGE);
  }

  /**
   * The CommandExecutor class is responsible for executing commands.
   * It maintains a mapping of commands to their corresponding execution methods.
   * <p>
   * Класът CommandExecutor е отговорен за изпълнението на команди.
   * Поддържа речник на команди към съответните им методи за изпълнение.
   */
  private static class CommandExecutor {

    /**
     * The mapping of commands to their execution methods.
     * <p>
     * Съпоставяне на команди с техните методи за изпълнение.
     */
    private final Map<Commands, Consumer<List<String>>> commandMap = new HashMap<>();

    /**
     * Initializes the command mapping.
     * <p>
     * Инициализира съпоставянето на командата.
     */
    private CommandExecutor() {
      commandMap.put(Commands.OPEN, this::executeOpenCommand);
      commandMap.put(Commands.PRINT, this::executePrintCommand);
      commandMap.put(Commands.EDIT, this::executeEditCommand);
      commandMap.put(Commands.CLOSE, this::executeCloseCommand);
      commandMap.put(Commands.SAVE, this::executeSaveCommand);
      commandMap.put(Commands.SAVEAS, this::executeSaveAsCommand);
      commandMap.put(Commands.HELP, this::executeHelpCommand);
      commandMap.put(Commands.EXIT, this::executeExitCommand);
    }

    /**
     * Executes the specified command with the given arguments.
     * <p>
     * Изпълнява посочената команда с дадените аргументи.
     *
     * @param command   The command to execute.
     *                  <p>
     *                  Командата за изпълнение.
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeCommand(Commands command, List<String> arguments) {
      commandMap.get(command).accept(arguments);
    }

    /**
     * Executes the "open" command with the given arguments.
     * <p>
     * Изпълнява командата "open" с дадените аргументи.
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeOpenCommand(List<String> arguments) {
      try {
        System.out.println(CommandFactory.getCommand(new OpenCommandFactory(arguments)).execute());
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    /**
     * Executes the "print" command.
     * <p>
     * Изпълнява командата "print".
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executePrintCommand(List<String> arguments) {
      try {
        System.out.println(CommandFactory.getCommand(new PrintCommandFactory()).execute());
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    /**
     * Executes the "edit" command with the given arguments.
     * <p>
     * Изпълнява командата "edit" с дадените аргументи.
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeEditCommand(List<String> arguments) {
      try {
        System.out.println(CommandFactory.getCommand(new EditCommandFactory(arguments)).execute());
      } catch (IllegalArgumentException | IOException e) {
        System.out.println(e.getMessage());
      }
    }

    /**
     * Executes the "close" command with the given arguments.
     * <p>
     * Изпълнява командата "close" с дадените аргументи.
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeCloseCommand(List<String> arguments) {
      try {
        System.out.println(CommandFactory.getCommand(new CloseCommandFactory()).execute());
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    /**
     * Executes the "save" command with the given arguments.
     * <p>
     * Изпълнява командата "save" с дадените аргументи.
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeSaveCommand(List<String> arguments) {
      try {
        System.out.println(CommandFactory.getCommand(new SaveCommandFactory()).execute());
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    /**
     * Executes the "saveas" command with the given arguments.
     * <p>
     * Изпълнява командата "saveas" с дадените аргументи.
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeSaveAsCommand(List<String> arguments) {
      try {
        System.out.println(CommandFactory.getCommand(new SaveAsCommandFactory(arguments)).execute());
      } catch (IllegalArgumentException | IOException e) {
        System.out.println(e.getMessage());
      }
    }

    /**
     * Executes the "help" command with the given arguments.
     * <p>
     * Изпълнява командата "help" с дадените аргументи.
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeHelpCommand(List<String> arguments) {
      try {
        System.out.println(CommandFactory.getCommand(new HelpCommandFactory()).execute());
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    /**
     * Executes the "exit" command with the given arguments.
     * <p>
     * Изпълнява командата "exit" с дадените аргументи.
     *
     * @param arguments The arguments for the command.
     *                  <p>
     *                  Аргументите за командата.
     */
    private void executeExitCommand(List<String> arguments) {
      System.exit(0);
    }
  }
}
