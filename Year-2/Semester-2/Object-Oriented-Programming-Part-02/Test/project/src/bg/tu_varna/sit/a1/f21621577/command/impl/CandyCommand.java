package bg.tu_varna.sit.a1.f21621577.command.impl;

import bg.tu_varna.sit.a1.f21621577.command.base.Command;

public class CandyCommand extends Command {

    private int quantity = 0;

    public CandyCommand(String commandName) {
        super(commandName);
    }

    @Override
    public Object getValue() {
        return quantity;
    }

    public CandyCommand(String commandName, int quantity) {
        super(commandName);
        this.quantity = quantity;
    }
}
