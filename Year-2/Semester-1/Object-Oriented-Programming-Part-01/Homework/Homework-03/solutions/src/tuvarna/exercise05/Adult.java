package tuvarna.exercise05;

public class Adult extends Person {

  private IdentificationCard identificationCard;

  public Adult(String firstName, String secondName, String thirdName, IdentificationCard identificationCard) {
    super(firstName, secondName, thirdName);
    this.identificationCard = identificationCard;
  }

  public String getPersonalInformation() {
    return String.format("%s%n%s",
            super.iAm(), identificationCard);
  }

  public IdentificationCard getIdentificationCard() {
    return identificationCard;
  }

  public void setIdentificationCard(IdentificationCard identificationCard) {
    this.identificationCard = identificationCard;
  }
}
