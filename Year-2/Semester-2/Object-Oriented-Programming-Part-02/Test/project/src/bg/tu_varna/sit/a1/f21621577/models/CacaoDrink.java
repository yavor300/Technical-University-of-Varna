package bg.tu_varna.sit.a1.f21621577.models;

public class CacaoDrink {

    private  CacaoType cacaoType;
    private  int sugar;
    private  int candies;

    public CacaoDrink() {
    }

    public CacaoDrink(CacaoType cacaoType) {
        this.cacaoType = cacaoType;
        this.sugar = 1;
        this.candies = 2;
    }

    public CacaoDrink(CacaoType cacaoType, int sugar, int candies) {
        this.cacaoType = cacaoType;
        this.sugar = sugar;
        this.candies = candies;
    }

    @Override
    public String toString() {
        return "Drink {" +
                "cacaoType=" + cacaoType +
                ", sugar=" + sugar +
                ", candies=" + candies +
                '}';
    }

    public CacaoDrink setCacaoType(CacaoType cacaoType) {
        this.cacaoType = cacaoType;
        return this;
    }

    public CacaoDrink setSugarQuantity(int sugar) {
        this.sugar = sugar;
        return this;
    }

    public CacaoDrink setCandiesQuantity(int candies) {
        this.candies = candies;
        return this;
    }
}
