package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.implementation.CommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.open.OpenCommandFactory;
import bg.tu_varna.sit.b1.f21621577.command.implementation.print.PrintCommandFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
      openCommand.execute();
    }
  }

  private static void executePrintCommand() throws IOException {
    Command printCommand = CommandFactory.getCommand(new PrintCommandFactory(Collections.emptyList()));
    if (printCommand != null) {
      printCommand.execute();
    }
  }
}