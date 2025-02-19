package bg.tu_varna.sit.a1.f21621577.chain.implementation;

import bg.tu_varna.sit.a1.f21621577.chain.CacaoDrinkChain;
import bg.tu_varna.sit.a1.f21621577.models.CacaoDrink;
import bg.tu_varna.sit.a1.f21621577.models.CacaoType;

public class CacaoStep extends CacaoDrinkChain {

    private final CacaoType cacaoType;

    public CacaoStep(CacaoType cacaoType) {
        this.cacaoType = cacaoType;
    }

    @Override
    public CacaoDrink execute(CacaoDrink cacaoDrink) {
        return cacaoDrink.setCacaoType(cacaoType);
    }

    @Override
    public String getSuitableForCommand() {
        return "Cacao";
    }
}
