package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.implementation.command.PrintCommand;
import bg.tu_varna.sit.b1.f21621577.implementation.command.SystemCommand;
import bg.tu_varna.sit.b1.f21621577.implementation.composite.CompositeCommand;
import bg.tu_varna.sit.b1.f21621577.implementation.proxy.RestrictedCommandProxy;

public class Main {

  public static void main(String[] args) {

    CompositeCommand allCommands = new CompositeCommand();
    allCommands.addCommand(new PrintCommand("Тест1"));
    allCommands.addCommand(new PrintCommand("Тест2"));
    allCommands.addCommand(new SystemCommand("ls"));

    allCommands.execute();

    CompositeCommand restrictedCommands = new CompositeCommand();
    restrictedCommands.addCommand(new RestrictedCommandProxy(new SystemCommand("dir"), false));

    try {
      restrictedCommands.execute();
    } catch (RuntimeException e) {
      System.out.println("Грешка при изпълнение: " + e.getMessage());
    }
  }
}
