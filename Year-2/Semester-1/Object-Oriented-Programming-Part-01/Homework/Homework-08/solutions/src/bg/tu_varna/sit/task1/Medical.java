package bg.tu_varna.sit.task1;

public class Medical<T> {

  private final T patient;
  private final Condition condition;

  public Medical(T patient, Condition condition) {
    this.patient = patient;
    this.condition = condition;
  }

  public int calculatePotion() {

    int result = 25;

    if (patient instanceof Child) {
      result =  (int) Math.ceil(0.250 * ((Child) patient).getWeight());
    }

    if (condition == Condition.damaged) {
      return result *  2;
    }

    return result;
  }

  public void getPrescription() {

    System.out.printf("%s%nДозировка: %d мл.%n", patient, calculatePotion());
  }

  public void getCoupon(String receiver) {

    System.out.printf("Издаден купон на: %s", receiver);
  }
}
