package bg.tu_varna.sit.b1.f21621577.implementation.proxy;

import bg.tu_varna.sit.b1.f21621577.base.Command;

public class RestrictedCommandProxy implements Command {

  private final Command command;
  private final boolean hasPermission;

  public RestrictedCommandProxy(Command command, boolean hasPermission) {
    this.command = command;
    this.hasPermission = hasPermission;
  }

  @Override
  public void execute() {

    if (hasPermission()) {
      command.execute();
    } else {
      throw new RuntimeException("Липсват права за изпълнението на програмата");
    }
  }

  private boolean hasPermission() {

    return hasPermission;
  }
}