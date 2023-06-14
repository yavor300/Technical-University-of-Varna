package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Command;

public class RemoteControlInvoker {

  private Command current;
  private Command last;

  public void setCurrent(Command current) {

    this.current = current;
  }

  public void executeSelectedCommand() {

    current.executeCommand();
    last = current;
  }

  public void undoCommand() {

    last.undoPreviousCommand();
  }
}
