package bg.tu_varna.sit.b1.f21621577.implementation.command;

import bg.tu_varna.sit.b1.f21621577.base.Command;

public class PrintCommand implements Command {

  private final String message;

  public PrintCommand(String message) {
    this.message = message;
  }

  @Override
  public void execute() {

    System.out.println(message);
  }
}