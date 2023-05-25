package bg.tu_varna.sit.b1.f21621577.chain.base;

import bg.tu_varna.sit.b1.f21621577.entities.Funnel;

public abstract class IceCreamChain {

  private IceCreamChain nextChain;

  public void setNextChain(IceCreamChain nextChain) {
    this.nextChain = nextChain;
  }

  public abstract String getFlavor();

  public Funnel execute(Funnel funnel) {

    funnel.addFlavor(getFlavor());
    return funnel;
  }

  public IceCreamChain getNextChain() {
    return nextChain;
  }
}
