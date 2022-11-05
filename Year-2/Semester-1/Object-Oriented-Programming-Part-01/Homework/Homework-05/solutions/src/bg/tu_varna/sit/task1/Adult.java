package bg.tu_varna.sit.task1;

public class Adult extends Person {

  private final IdentificationCard identificationCard;

  Adult(String firstName, String secondName, String thirdName, IdentificationCard identificationCard) {
    super(firstName, secondName, thirdName);
    this.identificationCard = identificationCard;
  }

  @Override
  public String toString() {

    return String.format("%s%n%s", super.toString(), identificationCard);
  }
}
