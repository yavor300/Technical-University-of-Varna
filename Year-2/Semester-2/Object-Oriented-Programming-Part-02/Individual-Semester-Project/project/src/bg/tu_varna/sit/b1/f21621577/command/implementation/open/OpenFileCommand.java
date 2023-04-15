package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.ArgumentCommand;
import bg.tu_varna.sit.b1.f21621577.table.reader.TableReader;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_RESOURCES_DIRECTORY;

/**
 * A command class that opens a table file and saves its contents in the table repository.
 */
public class OpenFileCommand extends ArgumentCommand {

  private Path file;

  /**
   * Constructs an OpenFileCommand instance.
   */
  public OpenFileCommand() {
  }

  /**
   * Constructs an OpenFileCommand instance with a list of arguments.
   *
   * @param arguments the list of arguments for the command
   */
  public OpenFileCommand(List<String> arguments) {
    super(arguments);
    this.file = Paths.get(DEFAULT_RESOURCES_DIRECTORY, arguments.get(0));
  }

  /**
   * Executes the command by opening the table file specified in the arguments, reading its contents, and saving them in the table repository.
   *
   * @return a message indicating whether the command was executed successfully or not
   */
  @Override
  public String execute() {

    try (TableReader tableReader = new TableReader(file)) {
      TableRepository.getInstance().save(tableReader.read());
      return "Table opened successfully.";
    } catch (IOException e) {
      return "Error opening table: " + e.getMessage();
    }
  }
}