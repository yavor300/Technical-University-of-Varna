package bg.tu_varna.sit.task5.impl;

import java.util.Map;

public class Exam {

  private final Discipline discipline;

  public Exam(Discipline discipline) {
    this.discipline = discipline;
  }

  public void run() {

    int examPoints = getRandomNumber(0, 60);

    for (Map.Entry<Student, Integer> entry : discipline.getStudents().entrySet()) {

      discipline.getStudents().put(entry.getKey(), entry.getValue() + examPoints);
      System.out.printf("%s%nTotal points: %d%n", entry.getKey(), entry.getValue());
    }

  }

  private int getRandomNumber(int min, int max) {

    return (int) ((Math.random() * (max - min)) + min);
  }
}
