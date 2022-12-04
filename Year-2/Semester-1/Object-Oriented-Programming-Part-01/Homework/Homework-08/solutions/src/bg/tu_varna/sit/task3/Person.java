package bg.tu_varna.sit.task3;

public class Person {

  private final String name;
  private final AgeGroup ageGroup;
  private final double productPrice;

  public Person(String name, AgeGroup ageGroup, double productPrice) {
    this.name = name;
    this.ageGroup = ageGroup;
    this.productPrice = productPrice;
  }

  public double calculateBaseDiscount() {

    if (ageGroup == AgeGroup.child || ageGroup == AgeGroup.pensioner) {
      return 0.08 * productPrice;
    }

    return 0.03 * productPrice;
  }

  public String getName() {
    return name;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public double getProductPrice() {
    return productPrice;
  }
}
