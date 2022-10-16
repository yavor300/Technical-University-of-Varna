package tuvarna.exercise08;

public class DrivingLicense {

  private final String id;
  private final int validityMonths;

  public DrivingLicense(String id, int validityMonths) {
    this.id = id;
    this.validityMonths = validityMonths;
  }

  @Override
  public String toString() {
    return String.format("Driving License:%nId: %s%nValidity: %d months",
            id, validityMonths);
  }
}
