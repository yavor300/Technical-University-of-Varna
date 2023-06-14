package bg.tu_varna.sit.b1.f21621577.entities;

class Person {

  private final String name;
  private final double assetValue;
  private boolean previousLoanExist;

  Person(String name, double assetValue, boolean previousLoanExist) {

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
