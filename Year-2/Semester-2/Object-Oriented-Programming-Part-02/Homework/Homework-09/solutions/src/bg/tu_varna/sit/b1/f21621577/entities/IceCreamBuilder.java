package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.chain.base.IceCreamChain;
import bg.tu_varna.sit.b1.f21621577.chain.implementation.ChocolateStep;
import bg.tu_varna.sit.b1.f21621577.chain.implementation.CreamStep;
import bg.tu_varna.sit.b1.f21621577.chain.implementation.PumpkinStep;
import bg.tu_varna.sit.b1.f21621577.chain.implementation.VanillaStep;
import java.util.List;

public class IceCreamBuilder {

  private IceCreamChain iceCreamChain;
  private final List<String> flavours;

  public IceCreamBuilder(List<String> flavours) {
    this.flavours = flavours;
    buildIceCreamChain();
  }

  private void buildIceCreamChain() {

    VanillaStep vanillaStep = new VanillaStep();
    ChocolateStep chocolateStep = new ChocolateStep();
    PumpkinStep pumpkinStep = new PumpkinStep();
    CreamStep creamStep = new CreamStep();

    vanillaStep.setNextChain(chocolateStep);
    chocolateStep.setNextChain(pumpkinStep);
    pumpkinStep.setNextChain(creamStep);

    iceCreamChain = vanillaStep;
  }

  public IceCreamChain getIceCreamChain() {
    return iceCreamChain;
  }

  public List<String> getFlavours() {
    return flavours;
  }
}