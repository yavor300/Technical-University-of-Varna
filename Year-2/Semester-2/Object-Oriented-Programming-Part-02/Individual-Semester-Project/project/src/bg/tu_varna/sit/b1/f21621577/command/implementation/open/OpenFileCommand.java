package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.table.reader.TableReader;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_RESOURCES_DIRECTORY;

public class OpenFileCommand extends Command {

  private Path file;

  public OpenFileCommand() {
  }

  public OpenFileCommand(List<String> arguments) {
    super(arguments);
    this.file = Paths.get(DEFAULT_RESOURCES_DIRECTORY, arguments.get(0));
  }

  @Override
  public boolean execute() throws IOException {
    try (TableReader tableReader = new TableReader(file)) {
      TableRepository.getInstance().save(tableReader.read());
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}