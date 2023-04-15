package bg.tu_varna.sit.b1.f21621577.command.implementation.edit;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.base.CommandAbstractFactory;

import java.util.List;

/**
 * A factory for creating an EditCommand object with a list of arguments.
 */
public class EditCommandFactory implements CommandAbstractFactory {

  /**
   * The list of arguments to be passed to the EditCommand.
   */
  private final List<String> arguments;

  /**
   * Constructs a new EditCommandFactory object with a list of arguments.
   *
   * @param arguments the list of arguments to be passed to the EditCommand
   */
  public EditCommandFactory(List<String> arguments) {
    this.arguments = arguments;
  }

  /**
   * Creates a new EditCommand object with the list of arguments.
   *
   * @return the new EditCommand object
   */
  @Override
  public Command createCommand() {
    return new EditCommand(arguments);
  }

}