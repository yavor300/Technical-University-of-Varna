package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.entities.LoanApprover;
import bg.tu_varna.sit.b1.f21621577.entities.Person;

public class Main {

  public static void main(String[] args) {

    LoanApprover loanApprover = new LoanApprover();
    Person person = new Person("Bob", 5000, true);
    System.out.println(loanApprover.checkLoanEligibility(person, 2000));
    person = new Person("Jack", 70000, false);
    System.out.println(loanApprover.checkLoanEligibility(person, 1250));
  }
}
