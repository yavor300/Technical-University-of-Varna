package bg.tu_varna.sit.b1.f21621577.command.impl;

import bg.tu_varna.sit.b1.f21621577.entities.Funnel;
import bg.tu_varna.sit.b1.f21621577.entities.IceCreamBuilder;
import bg.tu_varna.sit.b1.f21621577.chain.base.IceCreamChain;
import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import java.util.List;

public class MakeIceCreamCommand implements Command {

  private final IceCreamBuilder iceCreamBuilder;

  public MakeIceCreamCommand(IceCreamBuilder iceCreamBuilder) {
    this.iceCreamBuilder = iceCreamBuilder;
  }

  @Override
  public String execute() {

    Funnel funnel = createIceCream(iceCreamBuilder.getFlavours());

    StringBuilder flavors = new StringBuilder("Сладоледът във фунийката съдържа следните вкусове:\n");
    for (String flavor : funnel.getFlavors()) {
      flavors.append(flavor).append("\n");
    }

    return flavors.toString();
  }

  private Funnel createIceCream(List<String> commands) {

    Funnel funnel = new Funnel();

    for (String command : commands) {

      IceCreamChain step = findStepForCommand(command);
      if (step != null) {
        funnel = step.execute(funnel);
      }
    }

    return funnel;
  }

  private IceCreamChain findStepForCommand(String command) {

    IceCreamChain chain = iceCreamBuilder.getIceCreamChain();
    while (chain != null) {
      if (chain.getFlavor().equalsIgnoreCase(command)) {
        return chain;
      }
      chain = chain.getNextChain();
    }
    return null;
  }
}
