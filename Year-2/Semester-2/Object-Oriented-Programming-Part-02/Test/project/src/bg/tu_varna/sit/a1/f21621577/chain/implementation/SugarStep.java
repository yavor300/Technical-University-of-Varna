package bg.tu_varna.sit.a1.f21621577.chain.implementation;

import bg.tu_varna.sit.a1.f21621577.chain.CacaoDrinkChain;
import bg.tu_varna.sit.a1.f21621577.models.CacaoDrink;

public class SugarStep extends CacaoDrinkChain {

    private final int quantity;

    public SugarStep(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public CacaoDrink execute(CacaoDrink cacaoDrink) {
        return cacaoDrink.setSugarQuantity(quantity);
    }

    @Override
    public String getSuitableForCommand() {
        return "Sugar";
    }
}
