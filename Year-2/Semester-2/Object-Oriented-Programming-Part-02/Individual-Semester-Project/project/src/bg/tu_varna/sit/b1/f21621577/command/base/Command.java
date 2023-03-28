package bg.tu_varna.sit.b1.f21621577.command.base;

import java.io.IOException;

public abstract class Command {

  private final String[] arguments;

  public Command(String[] arguments) {
    this.arguments = arguments;
  }

  public abstract void execute() throws IOException;
}
