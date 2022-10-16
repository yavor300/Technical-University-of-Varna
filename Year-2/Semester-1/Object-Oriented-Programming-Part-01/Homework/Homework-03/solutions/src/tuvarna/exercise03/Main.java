package tuvarna.exercise03;

/**
 * Задача 3
 * <p>
 * Да се реализира клас Персонаж (Character), който има име (name) и живот (life).
 * Character се наследява от клас Защитник (Defender) и се разширява със защитно умение,
 * броня и лечителско умение, живота му е 250 при създаване.
 * Character се наследява от клас Атакуващ (Attacker) и се разширява
 * с ефективност, живота му е 150 при създаване. За двата класа
 * да се добавят методи за четене и запис на полетата.
 * Подберете подходящи модификатори за достъп.
 */
public class Main {

  public static void main(String[] args) {

    Character defender = new Defender("Tom", "running", 100, "healing");
    Character attacker = new Attacker("Rob", 100);
    System.out.printf("%s%n%n%s", defender, attacker);
  }
}
