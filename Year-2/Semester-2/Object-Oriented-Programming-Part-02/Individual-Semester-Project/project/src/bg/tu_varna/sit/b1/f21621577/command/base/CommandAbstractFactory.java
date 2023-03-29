package bg.tu_varna.sit.b1.f21621577.command.base;

/**
 * A factory interface for creating instances of the {@link Command} class.
 * Implementations of this interface should provide a way to create a new instance of a {@link Command} object,
 * which can then be executed by the application.
 */
public interface CommandAbstractFactory {

  /**
   * Creates a new instance of a {@link Command} object.
   *
   * @return a new instance of a {@link Command} object
   */
  Command createCommand();
}
