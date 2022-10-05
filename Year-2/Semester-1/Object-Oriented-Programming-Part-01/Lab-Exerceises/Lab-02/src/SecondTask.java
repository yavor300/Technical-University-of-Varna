/**
 * ТУ Варна организира събитие за 60 годишния си юбилеи.
 * Ръководството решава да организира събитието във външна зала, за да избере залата,
 * трябва да се изчисли стойността на средствата, който ще останат за наем на зала.
 * Общата стойност с която разполагат организаторите е 20 000лв
 *
 * Юбилейни награди – цената им е 20% от общата сума
 * Кетъринг – цената му е 15% по-малко от цената на статуетките
 * Озвучаване – цената му е ½ от цената на залата
 * Колко е стойността на всеки разход и колко средства остават за наем на зала
 */
public class SecondTask {
  private static final Double BUDGET = 20000.;

  public static void main(String[] args) {
    double awardsPrice = BUDGET * 0.2;
    double cateringPrice = awardsPrice - awardsPrice * 0.15;
    double soundPrice = (BUDGET - (awardsPrice + cateringPrice)) * (1.0 / 3.0);
    double hallPrice = (BUDGET - (awardsPrice + cateringPrice)) * (2.0 / 3.0);
    System.out.printf("Awards price: %.2f lv.%nCatering price: %.2f lv.%nSound price: %.2f lv.%nHall price: %.2f lv.",
            awardsPrice, cateringPrice, soundPrice, hallPrice);
  }
}
