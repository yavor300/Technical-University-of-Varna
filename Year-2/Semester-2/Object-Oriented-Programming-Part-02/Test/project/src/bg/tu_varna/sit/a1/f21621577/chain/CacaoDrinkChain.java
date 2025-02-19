package bg.tu_varna.sit.a1.f21621577.chain;

import bg.tu_varna.sit.a1.f21621577.models.CacaoDrink;

public abstract class CacaoDrinkChain {

  private CacaoDrinkChain nextChain;

  public void setNextChain(CacaoDrinkChain nextChain) {
    this.nextChain = nextChain;
  }

  public abstract CacaoDrink execute(CacaoDrink cacaoDrink);

  public abstract String getSuitableForCommand();

  public CacaoDrinkChain getNextChain() {
    return nextChain;
  }


}