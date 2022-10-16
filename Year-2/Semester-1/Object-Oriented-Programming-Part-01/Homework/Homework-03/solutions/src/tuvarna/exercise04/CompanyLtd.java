package tuvarna.exercise04;

import java.time.LocalDate;

public class CompanyLtd extends Company {

  private final String owner;
  private final double initialInvestment;
  private double currentBalance;

  public CompanyLtd(String name, LocalDate created, String id, String owner, double initialInvestment, double currentBalance) {
    super(name, created, id);
    this.owner = owner;
    this.initialInvestment = initialInvestment;
    this.currentBalance = currentBalance;
  }

  public double calculateBalanceDifference() {
    return currentBalance - initialInvestment;
  }

  @Override
  public String toString() {
    return String.format("%s%nOwner: %s%nInitial investment: %.2f BGN%nCurrent balance: %.2f BGN",
            super.toString(), getOwner(), getInitialInvestment(), getCurrentBalance());
  }

  public String getOwner() {
    return owner;
  }

  public double getInitialInvestment() {
    return initialInvestment;
  }

  public double getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(double currentBalance) {
    this.currentBalance = currentBalance;
  }
}
