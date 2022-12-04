package bg.tu_varna.sit.task3;

public class FoodDiscount implements Discount {

  private final Person[] people;
  private final double drinkDiscountRate;

  public FoodDiscount(Person[] people, double drinkDiscountRate) {
    this.people = people;
    this.drinkDiscountRate = drinkDiscountRate;
  }

  @Override
  public double calculateTotalDiscount() {

    int result = 0;

    for (Person person : people) {
      if (person.getAgeGroup() == AgeGroup.adult || person.getAgeGroup() == AgeGroup.pensioner) {
        result += (person.calculateBaseDiscount() + (drinkDiscountRate / 100) * person.getProductPrice());
      }
    }

    return result;
  }

  @Override
  public double calculateAverageDiscount() {

    try {
      return calculateTotalDiscount() / getValidPeopleCount();
    } catch (ArithmeticException e) {
      return 0;
    }
  }

  public Person[] getPeople() {
    return people;
  }

  public double getDrinkDiscountRate() {
    return drinkDiscountRate;
  }

  private int getValidPeopleCount() {

    int result = 0;

    for (Person person : people) {
      if (person.getAgeGroup() == AgeGroup.adult || person.getAgeGroup() == AgeGroup.pensioner) {
        result++;
      }
    }

    return result;
  }
}
