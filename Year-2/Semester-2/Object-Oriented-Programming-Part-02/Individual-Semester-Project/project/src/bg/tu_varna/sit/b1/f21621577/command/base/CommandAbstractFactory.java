package bg.tu_varna.sit.b1.f21621577.command.base;

/**
 * A factory interface for creating instances of the {@link Command} class.
 * Implementations of this interface should provide a way to create a new instance of a {@link Command} object,
 * which can then be executed by the application.
 * <p>
 * Фабричен интерфейс за създаване на инстанции на класа {@link Command}.
 * Реализациите на този интерфейс трябва да предоставят начин за създаване на нова инстанция на обект {@link Command},
 * които след това могат да бъдат изпълнени от приложението.
 */
public interface CommandAbstractFactory {

  /**
   * Creates a new instance of a {@link Command} object.
   * <p>
   * Създава нова инстанция на обект {@link Command}.
   *
   * @return a new instance of a {@link Command} object
   * <p>
   * нова инстанция на обект {@link Command}
   */
  Command createCommand();
}
