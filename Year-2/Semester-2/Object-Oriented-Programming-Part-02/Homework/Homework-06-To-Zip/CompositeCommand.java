package bg.tu_varna.sit.b1.f21621577.implementation.composite;

import bg.tu_varna.sit.b1.f21621577.base.Command;

import java.util.ArrayList;
import java.util.List;

public class CompositeCommand implements Command {

  private final List<Command> commands = new ArrayList<>();

  public void addCommand(Command command) {
    commands.add(command);
  }

  @Override
  public void execute() {

    for (Command command : commands) {
      command.execute();
    }
  }
}