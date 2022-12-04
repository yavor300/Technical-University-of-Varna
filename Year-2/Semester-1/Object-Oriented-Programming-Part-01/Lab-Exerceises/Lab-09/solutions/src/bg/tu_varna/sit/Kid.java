package bg.tu_varna.sit;

import bg.tu_varna.sit.base.Person;

public class Kid extends Person {

  private String[] allergies;

  public Kid(String name, int months, String[] allergies) throws DataException {
    super(name, months);
    this.allergies = allergies;
  }

  @Override
  public String toString() {

    StringBuilder allergiesPrint = new StringBuilder();

    for (String allergies : allergies) {
      allergiesPrint.append(allergies).append(" ");
    }

    return String.format("%s Алергии: %s", super.toString(), allergiesPrint);
  }

  public void setAllergies(String[] allergies) throws DataException {

    if (allergies == null) {
      throw new DataException();
    }
    this.allergies = allergies;
  }
}
