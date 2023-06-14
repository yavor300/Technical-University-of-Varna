package bg.tu_varna.sit.b1.f21621577.entities;

public class LoanApprover {

  private final Asset asset;
  private final LoanStatus loanStatus;

  public LoanApprover() {

    this.asset = new Asset();
    this.loanStatus = new LoanStatus();
  }

  public String checkLoanEligibility(Person person, double claimAmount) {

    String status = "Approved";
    String reason = "";

    System.out.println("\nChecking the loan approval status of " + person.getName());
    System.out.println("[The current asset value: " + person.getAssetValue() + ",\nclaim amount: " + claimAmount +
        ",\n existing loan?: " + person.isPreviousLoanExist() + "]\n");

    if (!asset.hasSufficientAssetValue(person, claimAmount)) {
      status = "Not approved.";
      reason += "\nInsufficient balance.";
    }

    if (loanStatus.hasPreviousLoans(person)) {
      status = "Not approved";
      reason += "\nAn old loan exists.";
    }

    String remarks = String.format("%nRemarks if any: %s", reason);

    return String.format("%s %s", status, remarks);
  }
}
