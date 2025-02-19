package bg.tu_varna.sit.a1.f21621577;

import bg.tu_varna.sit.a1.f21621577.chain.implementation.CacaoDrinkChainBuilder;
import bg.tu_varna.sit.a1.f21621577.command.base.Command;
import bg.tu_varna.sit.a1.f21621577.command.impl.CacaoCommand;
import bg.tu_varna.sit.a1.f21621577.command.impl.CandyCommand;
import bg.tu_varna.sit.a1.f21621577.command.impl.MakeCacaoDrinkCommand;
import bg.tu_varna.sit.a1.f21621577.command.impl.SugarCommand;
import bg.tu_varna.sit.a1.f21621577.models.CacaoType;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Command sugar = new SugarCommand("Sugar", 2);
        Command candies = new CandyCommand("candy", 4);
        Command coffee = new CacaoCommand("Cacao", CacaoType.BRAZIL);
        List<Command> commands = new ArrayList<>();
        commands.add(sugar);
        commands.add(candies);
        commands.add(coffee);
        CacaoDrinkChainBuilder builder = new CacaoDrinkChainBuilder(commands);

        MakeCacaoDrinkCommand command = new MakeCacaoDrinkCommand(builder);
        System.out.println(command.execute());
    }
}
