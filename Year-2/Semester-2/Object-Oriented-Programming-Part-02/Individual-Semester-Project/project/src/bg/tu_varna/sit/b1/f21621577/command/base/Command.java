package bg.tu_varna.sit.b1.f21621577.command.base;

import static bg.tu_varna.sit.b1.f21621577.constants.StatusCodes.SUCCESSFUL_STATUS_CODE;
import static bg.tu_varna.sit.b1.f21621577.constants.StatusCodes.UNSUCCESSFUL_STATUS_CODE;

/**
 * The Command class defines the behavior of a command that can be executed by the application.
 * <p>
 * Командният клас определя поведението на команда, която може да бъде изпълнена от приложението.
 */
public abstract class Command {

  /**
   * The status code representing the outcome of an operation.
   * By default, it is set to the successful status code.
   * <p>
   * Статусен код, който представя резултата от операция.
   * По подразбиране е настроен като успешен статусен код.
   */
  private int statusCode = UNSUCCESSFUL_STATUS_CODE;

  /**
   * Executes the command.
   * <p>
   * Изпълнява командата.
   *
   * @return a message indicating the result of the command execution
   * <p>
   * съобщение, показващо резултата от изпълнението на командата
   */
  public abstract String execute();

  /**
   * Retrieves the status code associated with the response.
   * <p>
   * Извлича статусния код, асоцииран с отговора.
   *
   * @return The status code of the response.
   * <p>
   * Статусният код на отговора.
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Sets the status code for the response.
   * <p>
   * Задава статусния код за отговора.
   *
   * @param statusCode The status code to set.
   *                   <p>
   *                   Статусният код, който да се зададе.
   */
  protected void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

}
