package solutions.test;

import solutions.test.People;

public class MainPeople {

  public static void main(String[] args) {

    People people = new People(
            Integer.parseInt("1d", 16),
            (char) Integer.parseInt("4d", 16),
            Double.parseDouble("0x1.0333333333333p4"),
            Double.parseDouble("0x1.6266666666666p6"),
            Double.parseDouble("0x1.38ccccccccccdp5"),
            Double.parseDouble("0x1.8733333333333p5")
    );

    System.out.println(people.toString());
  }
}
