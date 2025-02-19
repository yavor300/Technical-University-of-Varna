package bg.tu_varna.sit.a1.f21621577.command.base;

public abstract class Command {

    private final String commandName;

    public Command(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public abstract Object getValue();

}
