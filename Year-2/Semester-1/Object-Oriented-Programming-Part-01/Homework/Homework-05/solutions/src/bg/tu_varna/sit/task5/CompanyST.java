package bg.tu_varna.sit.task5;

class CompanyST extends Company {

  private String owner;
  private final boolean isPatentActivity;

  CompanyST(String name, String EIK, double incomes, double costs, String owner, boolean isPatentActivity) {
    super(name, EIK, incomes, costs);
    this.owner = owner;
    this.isPatentActivity = isPatentActivity;
  }

  @Override
  double calculateTaxes() {

    if (isPatentActivity) {
      return 500;
    }

    double operationalProfit = getIncomes() - getCosts();
    if (operationalProfit < 0) {
      return 0;
    }

    return 0.15 * operationalProfit;
  }
}
