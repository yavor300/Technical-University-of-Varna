package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

public class OpenCommandFactory implements CommandAbstractFactory {

  private final String[] arguments;

  public OpenCommandFactory(String[] arguments) {
    this.arguments = arguments;
  }

  @Override
  public Command createCommand() {

    return new OpenFileCommand(arguments);
  }
}
