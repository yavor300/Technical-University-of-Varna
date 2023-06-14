package bg.tu_varna.sit.b1.f21621577.entities;

class LoanStatus {

  boolean hasPreviousLoans(Person person) {

    System.out.println("Verifying " + person.getName() + "'s previous loan(s) status.");

    return person.isPreviousLoanExist();
  }
}
