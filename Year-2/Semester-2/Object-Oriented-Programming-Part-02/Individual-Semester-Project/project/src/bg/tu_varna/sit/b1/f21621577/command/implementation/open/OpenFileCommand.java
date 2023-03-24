package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.table.reader.TableReader;

import java.io.IOException;

public class OpenFileCommand extends Command {

  private final TableReader reader;

  public OpenFileCommand(String[] arguments, TableReader reader) {
    super(arguments);
    this.reader = reader;
  }

  @Override
  protected void execute() throws IOException {
    reader.read();
  }
}
