package bg.tu_varna.sit.b1.f21621577.entities;

class Asset {

  boolean hasSufficientAssetValue(Person person, double claimAmount) {

    System.out.println("Verifying " + person.getName() + "'s asset value");

    return person.getAssetValue() >= claimAmount;
  }
}
