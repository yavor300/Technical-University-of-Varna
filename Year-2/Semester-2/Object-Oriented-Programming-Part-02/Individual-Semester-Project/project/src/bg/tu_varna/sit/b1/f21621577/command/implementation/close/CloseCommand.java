package bg.tu_varna.sit.b1.f21621577.command.implementation.close;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.NO_TABLE_OPENED_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.TABLE_DATA_CLEARED_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.constants.StatusCodes.SUCCESSFUL_STATUS_CODE;
import static bg.tu_varna.sit.b1.f21621577.constants.StatusCodes.UNSUCCESSFUL_STATUS_CODE;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

/**
 * A command that closes the currently opened table by clearing its data from the repository.
 * <p>
 * Команда, която затваря текущо отворената таблица, като изчиства нейните данни от хранилището.
 */
public class CloseCommand extends Command {

  /**
   * The TableRepository instance used to perform the operation of closing a table.
   * <p>
   * Инстанцията TableRepository, използвана за извършване на операцията по затваряне на таблица.
   */
  private final TableRepository repository = TableRepository.getInstance();

  /**
   * Package-private constructor for creating a {@code CloseCommand} object.
   * <p>
   * Пакетно частен конструктор за създаване на обект {@code CloseCommand}.
   */
  CloseCommand() {
  }

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
      setStatusCode(SUCCESSFUL_STATUS_CODE);
      return TABLE_DATA_CLEARED_MESSAGE;
    } else {
      return NO_TABLE_OPENED_MESSAGE;
    }
  }
}
