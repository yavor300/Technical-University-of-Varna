package bg.tu_varna.sit.a1.f21621577.command.impl;

import bg.tu_varna.sit.a1.f21621577.chain.CacaoDrinkChain;
import bg.tu_varna.sit.a1.f21621577.chain.implementation.CacaoDrinkChainBuilder;
import bg.tu_varna.sit.a1.f21621577.command.base.Command;
import bg.tu_varna.sit.a1.f21621577.models.CacaoDrink;

import java.util.List;

public class MakeCacaoDrinkCommand {

  private final CacaoDrinkChainBuilder cacaoDrinkChainBuilder;

  public MakeCacaoDrinkCommand(CacaoDrinkChainBuilder cacaoDrinkChainBuilder) {
    this.cacaoDrinkChainBuilder = cacaoDrinkChainBuilder;
  }

  public String execute() {

    CacaoDrink drink = createCacaoDrink(cacaoDrinkChainBuilder.getCommandList());

 return drink.toString();
  }

  private CacaoDrink createCacaoDrink(List<Command> commands) {

    CacaoDrink drink = new CacaoDrink();

    for (Command command : commands) {

      CacaoDrinkChain step = findStepForCommand(command.getCommandName());
      if (step != null) {
        drink = step.execute(drink);
      }
    }

    return drink;
  }

  private CacaoDrinkChain findStepForCommand(String command) {

    CacaoDrinkChain chain = cacaoDrinkChainBuilder.getCacaoDrinkChain();
    while (chain != null) {
      if (chain.getSuitableForCommand().equalsIgnoreCase(command)) {
        return chain;
      }
      chain = chain.getNextChain();
    }
    return null;
  }
}