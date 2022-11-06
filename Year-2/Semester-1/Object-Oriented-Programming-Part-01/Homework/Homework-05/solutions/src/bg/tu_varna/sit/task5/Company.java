package bg.tu_varna.sit.task5;

abstract class Company {

  private final String name;
  private final String EIK;
  private final double incomes;
  private final double costs;

  protected Company(String name, String EIK, double incomes, double costs) {
    this.name = name;
    this.EIK = EIK;
    this.incomes = incomes;
    this.costs = costs;
  }

  abstract double calculateTaxes();

  protected String getName() {
    return name;
  }

  protected String getEIK() {
    return EIK;
  }

  protected double getIncomes() {
    return incomes;
  }

  protected double getCosts() {
    return costs;
  }
}
