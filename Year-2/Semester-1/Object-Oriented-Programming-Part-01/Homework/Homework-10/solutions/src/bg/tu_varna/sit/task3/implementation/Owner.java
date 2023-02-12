package bg.tu_varna.sit.task3.implementation;

import bg.tu_varna.sit.task3.base.Person;
import bg.tu_varna.sit.task3.exceptions.PersonalDataException;

public class Owner extends Person {

  private final String drivingLicense;

  public Owner(String egn, String firstName, String lastName, int age, String drivingLicense) throws PersonalDataException {
    super(egn, firstName, lastName, age);
    this.drivingLicense = drivingLicense;
  }

  public Owner(String egn, String firstName, String lastName, int age) throws PersonalDataException {
    super(egn, firstName, lastName, age);
    this.drivingLicense = "none";
  }

  @Override
  public boolean hasCompetence() {

    return getAge() >= 18 && getAge() <= 75 && !"none".equalsIgnoreCase(drivingLicense);
  }

  @Override
  public String toString() {

    return String.format("%s%nDriving license: %s", super.toString(), drivingLicense);
  }

  public String getDrivingLicense() {
    return drivingLicense;
  }

}
