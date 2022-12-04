package bg.tu_varna.sit.task1;

public class Adult {

  private final String name;
  private final String assuranceNumber;

  public Adult(String name, String assuranceNumber) {
    this.name = name;
    this.assuranceNumber = assuranceNumber;
  }

  @Override
  public String toString() {
    return String.format("Име: %s%nНомер на здравна осигуровка: %s", name, assuranceNumber);
  }
}
