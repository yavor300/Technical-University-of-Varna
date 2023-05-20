package bg.tu_varna.sit.b1.f21621577.command.implementation.open;

import bg.tu_varna.sit.b1.f21621577.command.base.ArgumentCommand;
import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_RESOURCES_DIRECTORY;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.ERROR_OPENING_TABLE_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.TABLE_OPENED_SUCCESSFULLY_MESSAGE;
import bg.tu_varna.sit.b1.f21621577.table.reader.TableReader;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A command class that opens a table file and saves its contents in the table repository.
 * <p>
 * Команден клас, който отваря файл с таблица и записва съдържанието му в хранилището на таблицата.
 */
public class OpenCommand extends ArgumentCommand {

  /**
   * The instance of the repository that stores the table.
   * <p>
   * Инстанцията на хранилището, което съхранява таблицата.
   */
  private final TableRepository repository = TableRepository.getInstance();

  /**
   * The path to the file that contains the table data.
   * <p>
   * Пътят до файла, който съдържа данните от таблицата.
   */
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
  OpenCommand(List<String> arguments) {
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
      return String.format(ERROR_OPENING_TABLE_MESSAGE, e.getMessage());
    }
  }
}