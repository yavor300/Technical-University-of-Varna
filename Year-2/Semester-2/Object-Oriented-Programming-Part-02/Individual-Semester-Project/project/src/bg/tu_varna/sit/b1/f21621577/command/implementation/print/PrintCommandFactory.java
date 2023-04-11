package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

import java.util.List;

/**
 * A factory for creating an PrintFileCommand object with a list of arguments.
 */
public class PrintCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the PrintFileCommand.
   */
  private List<String> arguments;

  /**
   * Constructs a new PrintFileCommand object with a list of arguments.
   *
   * @param arguments the list of arguments to be passed to the PrintFileCommand
   */
  public PrintCommandFactory(List<String> arguments) {
    setArguments(arguments);
  }

  /**
   * Creates a new PrintFileCommand object with the list of arguments.
   *
   * @return the new PrintFileCommand object
   */
  @Override
  public Command createCommand() {
    return new PrintFileCommand(arguments);
  }


  /**
   * TODO That method should be removed by creating an additional base class with arguments.
   *
   * @param arguments the list of arguments to be passed to the PrintFileCommand
   */
  public void setArguments(List<String> arguments) {
    this.arguments = arguments;
  }
}