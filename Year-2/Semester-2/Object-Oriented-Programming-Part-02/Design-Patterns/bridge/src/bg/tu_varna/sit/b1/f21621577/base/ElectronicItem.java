package bg.tu_varna.sit.b1.f21621577.base;

public abstract class ElectronicItem {

  private final String productType;
  private final PriceType priceType;

  protected ElectronicItem(PriceType priceType, String productType) {
    
    this.priceType = priceType;
    this.productType = productType;
  }

  public abstract void showPriceDetail();

  protected PriceType getPriceType() {

    return priceType;
  }

  protected String getProductType() {

    return productType;
  }
}
