package bg.tu_varna.sit.task5;

class CompanyLtd extends Company {

  private double capital;
  private final double profit;

  CompanyLtd(String name, String EIK, double incomes, double costs, double capital, double profit) {
    super(name, EIK, incomes, costs);
    this.capital = capital;
    this.profit = profit;
  }

  @Override
  double calculateTaxes() {

    double operationalProfit = getIncomes() - getCosts();
    if (operationalProfit < 0) {
      return 0;
    }

    if (profit < 0) {
      operationalProfit -= profit;
      if (operationalProfit < 0) {
        return 0;
      }
    }

    return 0.1 * operationalProfit;
  }
}
