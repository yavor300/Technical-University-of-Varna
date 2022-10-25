package bg.tu_varna.sit.task04;

/**
 * Анализирайте задачата и определите грешката (без да пренаписвате целия код),
 * така че да се намери колко пъти е участвал първия регистриран човек в томбула и колко е средната възраст на всички уникални участници.
 */

public class Application {

  public static void main(String[] args) {
    Person[] people = {new Person("Ivan", "Ivanov", "1122334455", 25),
            new Person("Ivan", "Ivanov", "2255882233", 23),
            new Person("Ivan", "Ivanov", "1020304050", 24),
            new Person("Ivan", "Ivanov", "1122334455", 22),
            new Person("Ivan", "Ivanov", "1122334655", 25),
            new Person("Ivan", "Ivanov", "1122334450", 25),
            new Person("Ivan", "Ivanov", "1122554455", 25),
            new Person("Ivan", "Ivanov", "1100334455", 23),
            new Person("Ivan", "Ivanov", "1122334455", 25),
            new Person("Ivan", "Ivanov", "1122354455", 25)};

    Person personToSearch = people[0];
    int count = 0;
    for (int i = 0; i < people.length; i++) {
      if (personToSearch.getId().equals(people[i].getId()))
        count++;
    }
    System.out.println("The person searched for is written " + count + " time(s) in the array");

    double averageAge = 0;
    int sum = 0;
    String used = "";
    for (int i = 0; i < people.length; i++) {
      if (!used.contains(people[i].getId())) {
        used += "#" + people[i].getId();
        sum += people[i].getAge();
      }
      averageAge = (double) sum / i;
    }
    System.out.println("The average age of the people in the array is " + averageAge);
  }
}
