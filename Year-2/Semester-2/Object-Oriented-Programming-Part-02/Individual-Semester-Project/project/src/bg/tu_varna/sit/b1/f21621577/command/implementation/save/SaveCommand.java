package bg.tu_varna.sit.b1.f21621577.command.implementation.save;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;
import bg.tu_varna.sit.b1.f21621577.table.writer.TableWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_RESOURCES_DIRECTORY;

/**
 * Command class that saves the current table to a file.
 */
public class SaveCommand implements Command {


  private final TableRepository repository = TableRepository.getInstance();
  private final Path filePath;

  /**
   * Constructs a new instance of the SaveCommand class with a default file path for the table file
   * that is obtained by concatenating the default resources directory and the file name of the currently
   * opened table in the repository.
   */
  public SaveCommand() {
    this.filePath = Paths.get(DEFAULT_RESOURCES_DIRECTORY, repository.getTableFileName());
  }

  /**
   * Executes the command to save the changes made to the opened table to a file.
   *
   * @return a message indicating the result of the command execution
   */
  @Override
  public String execute() {

    if (!repository.isTableOpened()) {
      return "No table is currently opened.";
    }

    try (TableWriter writer = new TableWriter(filePath)) {
      writer.write(repository.getTable());
    } catch (IOException e) {
      return "Failed to save the table to file: " + e.getMessage();
    }

    return "Table saved to file \"" + filePath.toString() + "\"";
  }
}