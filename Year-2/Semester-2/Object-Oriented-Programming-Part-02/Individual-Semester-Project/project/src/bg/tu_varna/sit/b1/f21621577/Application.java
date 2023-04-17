package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.implementation.CommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.close.CloseCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.edit.EditCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.open.OpenCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.print.PrintCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.save.SaveCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.saveas.SaveAsCommandFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {

  public static void main(String[] args) {

    try (Scanner scanner = new Scanner(System.in)) {

      String menuChoice;
      do {

        List<String> input = new ArrayList<>(Arrays.asList(scanner.nextLine().trim().split("\\s+")));

        menuChoice = input.get(0).toUpperCase();
        switch (menuChoice) {
          case "OPEN":
            input.remove(0);
            executeOpenCommand(input);
            break;
          case "PRINT":
            executePrintCommand();
            break;
          case "EDIT":
            input.remove(0);
            executeEditCommand(input);
            break;
          case "CLOSE":
            executeCloseCommand();
            break;
          case "SAVE":
            executeSaveCommand();
            break;
          case "SAVEAS":
            input.remove(0);
            executeSaveAsCommand(input);
            break;
          case "EXIT":
            break;
          default:
            System.out.println("Invalid choice: " + menuChoice);
            break;
        }
      } while (!menuChoice.equalsIgnoreCase("EXIT"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void executeOpenCommand(List<String> input) throws IOException {
    Command openCommand = CommandFactory.getCommand(new OpenCommandFactory(input));
    if (openCommand != null) {
      System.out.println(openCommand.execute());
    }
  }

  private static void executePrintCommand() throws IOException {
    Command printCommand = CommandFactory.getCommand(new PrintCommandFactory());
    if (printCommand != null) {
      System.out.println(printCommand.execute());
    }
  }

  private static void executeEditCommand(List<String> arguments) throws IOException {
    Command editCommand = CommandFactory.getCommand(new EditCommandFactory(arguments));
    if (editCommand != null) {
      System.out.println(editCommand.execute());
    }
  }

  private static void executeCloseCommand() throws IOException {
    Command closeCommand = CommandFactory.getCommand(new CloseCommandFactory());
    if (closeCommand != null) {
      System.out.println(closeCommand.execute());
    }
  }

  private static void executeSaveCommand() throws IOException {
    Command saveCommand = CommandFactory.getCommand(new SaveCommandFactory());
    if (saveCommand != null) {
      System.out.println(saveCommand.execute());
    }
  }

  private static void executeSaveAsCommand(List<String> arguments) throws IOException {
    Command saveAsCommand = CommandFactory.getCommand(new SaveAsCommandFactory(arguments));
    if (saveAsCommand != null) {
      System.out.println(saveAsCommand.execute());
    }
  }
}