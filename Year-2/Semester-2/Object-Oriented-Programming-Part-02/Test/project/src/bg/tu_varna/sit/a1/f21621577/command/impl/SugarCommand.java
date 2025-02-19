package bg.tu_varna.sit.a1.f21621577.command.impl;

import bg.tu_varna.sit.a1.f21621577.command.base.Command;

public class SugarCommand extends Command {

    private int quantity = 0;

    public SugarCommand(String commandName) {
        super(commandName);
    }

    @Override
    public Object getValue() {
        return quantity;
    }

    public SugarCommand(String commandName, int quantity) {
        super(commandName);
        this.quantity = quantity;
    }
}
