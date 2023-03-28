package bg.tu_varna.sit.b1.f21621577.command.implementation;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

public class CommandFactory {

  public static Command getCommand(CommandAbstractFactory factory) {
    return factory.createCommand();
  }
}
