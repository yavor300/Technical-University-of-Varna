package bg.tu_varna.sit.base;

import bg.tu_varna.sit.DataException;

public class Person {

  private String name;
  private int months;

  protected Person(String name, int months) throws DataException {
    setName(name);
    setMonths(months);
  }

  @Override
  public String toString() {
    return String.format("%s %s", name, months);
  }

  private void setName(String name) throws DataException {

    if (name == null || name.trim().isEmpty()) {
      throw new DataException();
    }
    this.name = name;
  }

  private void setMonths(int months) throws DataException {

    if (months < 0 || months > 1200) {
      throw new DataException();
    }
    this.months = months;
  }
}
