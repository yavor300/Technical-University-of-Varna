package bg.tu_varna.sit.b1.f21621577.implementation.command;

import bg.tu_varna.sit.b1.f21621577.base.Command;

public class SystemCommand implements Command {

  private final String command;

  public SystemCommand(String command) {
    this.command = command;
  }

  @Override
  public void execute() {

    try {
      Runtime.getRuntime().exec(command);
    } catch (Exception e) {
      throw new RuntimeException("Грешка при изпълнение на командата: " + command, e);
    }
  }
}