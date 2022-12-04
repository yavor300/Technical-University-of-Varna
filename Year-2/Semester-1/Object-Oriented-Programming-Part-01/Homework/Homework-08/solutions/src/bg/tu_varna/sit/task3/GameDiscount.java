package bg.tu_varna.sit.task3;

public class GameDiscount implements  Discount {

  private final Person[] people;
  private final int discountRate;
  private final int discountRateForAdults;

  public GameDiscount(Person[] people, int discountRate, int discountRateForAdults) {
    this.people = people;
    this.discountRate = discountRate;
    this.discountRateForAdults = discountRateForAdults;
  }

  @Override
  public double calculateTotalDiscount() {

    int result = 0;

    for (Person person : people) {
      if (person.getAgeGroup() == AgeGroup.adult) {
        result += (person.calculateBaseDiscount() + (discountRateForAdults / 100) * person.getProductPrice());
      } else {
        result += (person.calculateBaseDiscount() + (discountRate / 100) * person.getProductPrice());
      }
    }

    return result;
  }

  @Override
  public double calculateAverageDiscount() {

    return calculateTotalDiscount() / people.length;
  }

  public Person[] getPeople() {
    return people;
  }

  public int getDiscountRate() {
    return discountRate;
  }

  public int getDiscountRateForAdults() {
    return discountRateForAdults;
  }
}
