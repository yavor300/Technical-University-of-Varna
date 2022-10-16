package tuvarna.exercise04;

import java.time.LocalDate;

/**
 * Задача 4
 * <p>
 * Да се реализира клас Фирма (Company), определящ се от Име на фирмата,
 * Дата на създаване на фирмата, Булстат – уникален 10 знаков код,
 * включващ букви и цифри, методи за извличане стойностите на полетата.
 * Подберете подходящи модификатори за достъп.
 * Класа Фирма се наследява от Фирма-ООД(CompanyLtd), който я разширява
 * с Име на собственика, учредил фирмата, Първоначален капитал, Актуален капитал,
 * и методи за четене на полетата и промяна на актуалния капитал.
 * Класът има метод, който намира изменението на капитала от момента на учредяването.
 * Той трябва да връща разликата между актуалния и първоначалния капитал на съответната фирма.
 * Подберете подходящи модификатори за достъп при всеки клас.
 */
public class Main {

  public static void main(String[] args) {

    CompanyLtd companyLtd = new CompanyLtd(
            "Tesla",
            LocalDate.of(2012, 1, 1),
            "00000T35LA",
            "Elon Musk",
            10000.0,
            100000.0);

    companyLtd.setCurrentBalance(companyLtd.getCurrentBalance() + 1000.50);
    System.out.printf("Difference between initial and current balance: %.2f BGN%n",
            companyLtd.calculateBalanceDifference());
    System.out.println(companyLtd);
  }
}
