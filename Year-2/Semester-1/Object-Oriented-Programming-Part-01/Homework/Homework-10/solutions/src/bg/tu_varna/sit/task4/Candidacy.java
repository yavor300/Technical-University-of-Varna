package bg.tu_varna.sit.task4;

import bg.tu_varna.sit.task4.enums.Party;

public class Candidacy implements Comparable<Candidacy> {

  private int candidacyId;
  private Person candidatePresident;
  private Person candidateVicePresident;
  private Party party;

  public Candidacy(int candidacyId, Person candidatePresident, Person candidateVicePresident, Party party) {
    this.candidacyId = candidacyId;
    this.candidatePresident = candidatePresident;
    this.candidateVicePresident = candidateVicePresident;
    this.party = party;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Candidacy candidacy = (Candidacy) o;

    return candidacyId == candidacy.candidacyId;
  }

  @Override
  public int hashCode() {
    return candidacyId;
  }

  @Override
  public String toString() {
    return String.format("Candidacy id: %d%nCandidate president:%n%s%nCandidate vice president:%n%s%nParty: %s",
            candidacyId, candidatePresident, candidateVicePresident, party);
  }

  public int getCandidacyId() {
    return candidacyId;
  }

  public void setCandidacyId(int candidacyId) {
    this.candidacyId = candidacyId;
  }

  public Person getCandidatePresident() {
    return candidatePresident;
  }

  public void setCandidatePresident(Person candidatePresident) {
    this.candidatePresident = candidatePresident;
  }

  public Person getCandidateVicePresident() {
    return candidateVicePresident;
  }

  public void setCandidateVicePresident(Person candidateVicePresident) {
    this.candidateVicePresident = candidateVicePresident;
  }

  public Party getParty() {
    return party;
  }

  public void setParty(Party party) {
    this.party = party;
  }

  @Override
  public int compareTo(Candidacy o) {

    return Integer.compare(candidacyId, o.getCandidacyId());
  }
}
