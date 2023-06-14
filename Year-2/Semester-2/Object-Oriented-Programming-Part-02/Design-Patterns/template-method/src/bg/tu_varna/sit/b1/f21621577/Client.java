package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.BasicEngineering;
import bg.tu_varna.sit.b1.f21621577.implementation.ComputerScience;
import bg.tu_varna.sit.b1.f21621577.implementation.Electronics;

public class Client {

  public static void main(String[] args) {

    BasicEngineering preferredCourse = new ComputerScience();
    System.out.println("Computer Science course structure:");
    preferredCourse.displayCourseStructure();
    System.out.println();
    preferredCourse = new Electronics();
    System.out.println("Electronics course structure");
    preferredCourse.displayCourseStructure();
  }
}
