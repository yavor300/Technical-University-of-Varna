package tuvarna.exercise02;

/**
 * Задача 2
 * <p>
 * Да се реализира клас Котка (Cat) която има цвят (color) и дължина на козината (furSize).
 * Класът Котка се наследява от клас Sphynx. За двата класа да се добавят методи за четене и
 * запис на полетата, и конструктор, като обектите от класа Sphynx задължително трябва да
 * имат дължина на козината 0. Подберете подходящи модификатори за достъп.
 */
public class Main {

  public static void main(String[] args) {

    Cat sphynx = new Sphynx("black");
    System.out.println(sphynx);
  }
}
