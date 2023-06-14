package bg.tu_varna.sit.b1.f21621577.entities;

public class Person {

  private final String name;
  private final double assetValue;
  private final boolean previousLoanExist;

  public Person(String name, double assetValue, boolean previousLoanExist) {

    this.name = name;
    this.assetValue = assetValue;
    this.previousLoanExist = previousLoanExist;
  }

  String getName() {

    return name;
  }

  double getAssetValue() {

    return assetValue;
  }

  boolean isPreviousLoanExist() {

    return previousLoanExist;
  }


}
