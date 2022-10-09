package exercise09;

public class SoftDrink {
  private String name;
  private String expirationDate;
  private String size;
  private String sugar;

  public SoftDrink(String name, String expirationDate, String size, String sugar) {
    this.name = name;
    this.expirationDate = expirationDate;
    this.size = size;
    this.sugar = sugar;
  }

  @Override
  public String toString() {
    return String.format("Name: %s%nExpiration date: %s%nSize: %s%nSugar: %.2f per 100 ml.",
            getName(), getExpirationDate(), getSize(), Double.parseDouble(getSugar()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SoftDrink softDrink = (SoftDrink) o;

    if (!name.equals(softDrink.name)) return false;
    return sugar.equals(softDrink.sugar);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getSugar() {
    return sugar;
  }

  public void setSugar(String sugar) {
    this.sugar = sugar;
  }
}
