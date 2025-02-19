package bg.tu_varna.sit.a1.f21621577.command.impl;

import bg.tu_varna.sit.a1.f21621577.command.base.Command;
import bg.tu_varna.sit.a1.f21621577.models.CacaoType;

public class CacaoCommand extends Command {

    private CacaoType cacaoType;

    public CacaoCommand(String commandName, CacaoType cacaoType) {
        super(commandName);
        this.cacaoType = cacaoType;
    }

    @Override
    public Object getValue() {
        return cacaoType;
    }
}
