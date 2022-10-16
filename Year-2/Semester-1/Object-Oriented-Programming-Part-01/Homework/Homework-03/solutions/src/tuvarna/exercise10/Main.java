package tuvarna.exercise10;

/**
 * Задача 10
 * <p>
 * Да се реализират класове, описващи окръжност, конус и цилиндър.
 * Всеки един от класовете да съдържа по два конструктора – един с параметри
 * и един по подразбиране, които инициализират стойностите на полетата.
 * Класът, описващ окръжност, да съдържа метод за пресмятане лице на кръг,
 * а класовете описващи цилиндър и конус – методи за пресмятане обема на фигурите.
 * Общите свойства на фигурите да се изведат в общ родителски клас.
 * Подберете подходящи модификатори за достъп при всеки клас.
 */
public class Main {

  public static void main(String[] args) {

    Circle circle = new Circle(2);
    System.out.printf("Circle area: %.2f%n", circle.getArea());
    Cone cone = new Cone(4, 6);
    System.out.printf("Cone volume: %.2f%n", cone.getVolume());
    Cylinder cylinder = new Cylinder(3, 9);
    System.out.printf("Cylinder volume: %.2f", cylinder.getVolume());
  }
}
