package bg.tu_varna.sit.b1.f21621577.command.implementation.close;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

/**
 * A command that closes the currently opened table by clearing its data from the repository.
 * <p>
 * Команда, която затваря текущо отворената таблица, като изчиства нейните данни от хранилището.
 */
public class CloseCommand implements Command {

  private final TableRepository repository = TableRepository.getInstance();

  /**
   * Executes the close command by clearing the data of the currently opened table from the repository.
   * <p>
   * Изпълнява командата за затваряне, като изчиства данните от текущо отворената таблица от хранилището.
   *
   * @return a message indicating whether the table data was cleared successfully or if no table was currently opened.
   * <p>
   * съобщение, указващо дали данните от таблицата са изчистени успешно или в момента няма отворена таблица.
   */
  @Override
  public String execute() {

    if (repository.isTableOpened()) {
      repository.clear();
      return "Table data cleared successfully.";
    } else {
      return "No table is currently opened.";
    }
  }
}
