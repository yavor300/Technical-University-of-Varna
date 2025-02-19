package bg.tu_varna.sit.a1.f21621577.chain.implementation;

import bg.tu_varna.sit.a1.f21621577.chain.CacaoDrinkChain;
import bg.tu_varna.sit.a1.f21621577.command.base.Command;
import bg.tu_varna.sit.a1.f21621577.models.CacaoType;

import java.util.List;

public class CacaoDrinkChainBuilder {

  private List<Command> commandList;
  private CacaoDrinkChain cacaoDrinkChain;

  public CacaoDrinkChainBuilder(List<Command> commandList) {
    this.commandList = commandList;
    buildChain();
  }

  private void buildChain() {

    CacaoStep cacaoStep = null;
    SugarStep sugarStep = null;
    CandyStep candyStep = null;

    for (Command command : commandList) {
      if (command.getCommandName().equalsIgnoreCase("Sugar")) {
        sugarStep = new SugarStep((Integer) command.getValue());
      } else if (command.getCommandName().equalsIgnoreCase("Cacao")) {
        cacaoStep = new CacaoStep((CacaoType) command.getValue());
      } else {
        candyStep = new CandyStep((Integer) command.getValue());
      }
    }

    cacaoStep.setNextChain(sugarStep);
    sugarStep.setNextChain(candyStep);

    cacaoDrinkChain = cacaoStep;
  }

  public CacaoDrinkChain getCacaoDrinkChain() {
    return cacaoDrinkChain;
  }

  public List<Command> getCommandList() {
    return commandList;
  }
}