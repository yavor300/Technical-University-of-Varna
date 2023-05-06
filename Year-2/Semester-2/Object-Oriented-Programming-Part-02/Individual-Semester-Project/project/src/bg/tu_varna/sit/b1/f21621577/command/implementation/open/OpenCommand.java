package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.ArgumentCommand;
import bg.tu_varna.sit.b1.f21621577.table.reader.TableReader;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.*;

/**
 * A command class that opens a table file and saves its contents in the table repository.
 * <p>
 * Команден клас, който отваря файл с таблица и записва съдържанието му в хранилището на таблицата.
 */
public class OpenCommand extends ArgumentCommand {

  private final TableRepository repository = TableRepository.getInstance();
  private final Path file;

  /**
   * Constructs an OpenCommand instance with a list of arguments.
   * <p>
   * Конструира инстанция на OpenCommand със списък от аргументи.
   *
   * @param arguments the list of arguments for the command
   *                  <p>
   *                  списъка с аргументи за командата
   */
  public OpenCommand(List<String> arguments) {
    super(arguments);
    this.file = Paths.get(DEFAULT_RESOURCES_DIRECTORY, arguments.get(0));
  }

  /**
   * Executes the command by opening the table file specified in the arguments, reading its contents,
   * and loading them in the table repository.
   * <p>
   * Изпълнява командата, като отваря файла с таблицата, посочен в аргументите, чете съдържанието му,
   * и го зарежда в хранилището на таблицата.
   *
   * @return a message indicating whether the command was executed successfully or not
   * <p>
   * съобщение, показващо дали командата е изпълнена успешно или не
   */
  @Override
  public String execute() {

    try (TableReader tableReader = new TableReader(file)) {
      repository.loadData(tableReader.read());
      repository.setTableFileName(file.getFileName().toString());
      return TABLE_OPENED_SUCCESSFULLY_MESSAGE;
    } catch (IOException | IllegalArgumentException e) {
      return String.format(ERROR_OPENING_TABLE, e.getMessage());
    }
  }
}