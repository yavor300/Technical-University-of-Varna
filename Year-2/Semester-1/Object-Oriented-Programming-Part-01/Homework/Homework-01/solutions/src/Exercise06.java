/**
 * Задача 6
 * Създайте масив, който да съхранява средните борсови цени на пшеницата през последните 9 месеца
 * (от началото на 2022 г.): {670.50, 687.22, 691.05, 710.56, 750.82, 788.44, 765.97, 698.36, 778,78}.
 * Напишете програма, която да извежда номерата на месеците, през които цената е била над средната за периода.
 */
public class Exercise06 {

  public static void main(String[] args) {
    double[] prices = {670.50, 687.22, 691.05, 710.56, 750.82, 788.44, 765.97, 698.36, 778,78};
    double average = 0;
    for (double price : prices) {
      average += price;
    }
    average /= prices.length;

    for (int i = 0; i < prices.length; i++) {
      if (prices[i] > average) {
        System.out.println(i + 1);
      }
    }
  }
}
