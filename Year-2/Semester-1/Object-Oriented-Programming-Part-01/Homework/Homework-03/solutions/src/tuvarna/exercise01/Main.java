package tuvarna.exercise01;

/**
 * Задача 1
 * <p>
 * Да се реализира клас Машина (Machine) която има цена (price).
 * Машината се наследява от клас Принтер (Printer) и се разширява с
 * брой на страници (numberOfPages), които се принтират за 1 минута.
 * За двата класа да се добавят методи за четене и запис на полетата.
 * Подберете подходящи модификатори за достъп.
 */
public class Main {

  public static void main(String[] args) {

    Machine printer = new Printer(35.00, 900);
    System.out.printf("Printer info:%n%s", printer);
  }
}
