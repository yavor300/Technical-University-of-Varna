package bg.tu_varna.sit.b1.f21621577.command.implementation.save;

import bg.tu_varna.sit.b1.f21621577.command.base.ArgumentCommand;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;
import bg.tu_varna.sit.b1.f21621577.table.writer.TableWriter;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.*;

/**
 * Command class that saves the current table to a file.
 * <p>
 * Клас команда, която записва текущата таблица във файл.
 */
public class SaveCommand extends ArgumentCommand {

  /**
   * The table repository instance.
   * <p>
   * Инстанцията на хранилището на таблицата.
   */
  private final TableRepository repository = TableRepository.getInstance();

  /**
   * The file path where the table should be saved.
   * <p>
   * Пътят на файла, където трябва да бъде запазена таблицата.
   */
  private final Path filePath;

  /**
   * Constructs a new instance of the SaveCommand class with a default file path for the table file
   * that is obtained by concatenating the default resources directory and the file name of the currently
   * opened table in the repository.
   * <p>
   * Конструира нова инстанция на класа SaveCommand с път към файл по подразбиране за файла на таблицата
   * което се получава чрез свързване на директорията с ресурси по подразбиране и името на файла на текущо
   * отворената таблица в хранилището.
   */
  SaveCommand() {
    this.filePath = Paths.get(DEFAULT_RESOURCES_DIRECTORY, repository.getTableFileName());
  }

  /**
   * A command to save the table data to a file.
   * <p>
   * Команда за запазване на данните от таблицата във файл.
   *
   * @param arguments a list of arguments passed to the command
   *                  <p>
   *                  списък от аргументи, предадени на командата
   * @throws IllegalArgumentException if the arguments list is null or empty
   *                                  <p>
   *                                  ако списъкът с аргументи е нулев или празен
   * @throws IllegalArgumentException if the first argument is null or empty
   *                                  <p>
   *                                  ако първият аргумент е нулев или празен
   * @throws InvalidPathException     if the file path is invalid
   *                                  <p>
   *                                  ако пътят на файла е невалиден
   */
  public SaveCommand(List<String> arguments) {

    super(arguments);

    if (arguments == null || arguments.isEmpty()) {
      throw new IllegalArgumentException(MISSING_FILE_ARGUMENT_ERROR);
    }

    this.filePath = Paths.get(DEFAULT_RESOURCES_DIRECTORY, arguments.get(0));
  }

  /**
   * Executes the command to save the changes made to the opened table to a file.
   * <p>
   * Изпълнява командата за запазване на направените промени в отворената таблица във файл.
   *
   * @return a message indicating the result of the command execution
   * <p>
   * съобщение, показващо резултата от изпълнението на командата
   */
  @Override
  public String execute() {

    if (!repository.isTableOpened()) {
      return NO_TABLE_OPENED_MESSAGE;
    }

    try (TableWriter writer = new TableWriter(filePath)) {
      writer.write(repository.getTable());
    } catch (IOException e) {
      return String.format(ERROR_SAVING_CHANGES, e.getMessage());
    }

    return String.format(TABLE_SAVED_MESSAGE, filePath.toString());
  }
}
