package bg.tu_varna.sit;

import bg.tu_varna.sit.base.Person;

public class Adult extends Person {

  private String[] diseases;

  public Adult(String name, int months, String[] diseases) throws DataException {
    super(name, months);
    setDiseases(diseases);
  }

  @Override
  public String toString() {

    StringBuilder diseasesPrint = new StringBuilder();

    for (String disease : diseases) {
      diseasesPrint.append(disease).append(" ");
    }

    return String.format("%s Заболявания: %s", super.toString(), diseasesPrint);
  }

  private void setDiseases(String[] diseases) throws DataException {

    if (diseases == null) {
      throw new DataException();
    }
    this.diseases = diseases;
  }
}
