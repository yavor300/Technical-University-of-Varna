package bg.tu_varna.sit.task4;

import bg.tu_varna.sit.task4.enums.Party;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Election {

  private String dateOfElection;
  private Map<Candidacy, Integer> results;

  public Election(String dateOfElection) {
    this.dateOfElection = dateOfElection;
    this.results = new HashMap<>();
  }

  public void addResult(Candidacy candidacy, Integer results) {

    this.results.put(candidacy, results);
  }

  public void printOrderedByCandidacyId() {

    Map<Candidacy, Integer> result = new TreeMap<>(this.results);
    for (Map.Entry<Candidacy, Integer> entry : result.entrySet()) {
      System.out.printf("%s - %s%n",
              entry.getKey().getCandidatePresident(), entry.getKey().getCandidateVicePresident());
    }
  }

  public void printOrderedByVotes() {

    List<Map.Entry<Candidacy, Integer>> entries = new ArrayList<>(results.entrySet());
    Collections.sort(entries, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
    for (Map.Entry<Candidacy, Integer> entry : entries) {
      System.out.printf("%s - %s",
              entry.getKey().getCandidatePresident(), entry.getKey().getCandidateVicePresident());
    }
  }

  public int getVotesByPerson(Person person) {

    int result = 0;

    for (Map.Entry<Candidacy, Integer> entry : results.entrySet()) {
      if (entry.getKey().getCandidatePresident().equals(person)
              || entry.getKey().getCandidateVicePresident().equals(person)) {
        result = entry.getValue();
      }
    }

    return result;
  }

  public void getUnsupportedCandidacies() {

    for (Map.Entry<Candidacy, Integer> entry : results.entrySet()) {
      if (entry.getKey().getParty() == Party.NONE) {
        System.out.printf("%s - %s",
                entry.getKey().getCandidatePresident(), entry.getKey().getCandidateVicePresident());
      }
    }
  }

  public void calculateElectionResults() {

    List<Map.Entry<Candidacy, Integer>> entries = new ArrayList<>(results.entrySet());
    Collections.sort(entries, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

    Integer mostVotes = entries.get(0).getValue();
    Double totalVotes = 0.;
    for (Map.Entry<Candidacy, Integer> entry : entries) {
      totalVotes += entry.getValue();
    }

    if ((mostVotes / totalVotes) * 100 > 50) {
      System.out.printf("%s - %s са изборът на нацията.",
              entries.get(0).getKey().getCandidatePresident(),
              entries.get(0).getKey().getCandidateVicePresident());
    } else {
      System.out.println("Втори тур.");
    }
  }

  public String getDateOfElection() {
    return dateOfElection;
  }

  public void setDateOfElection(String dateOfElection) {
    this.dateOfElection = dateOfElection;
  }

  public Map<Candidacy, Integer> getResults() {
    return results;
  }

  public void setResults(Map<Candidacy, Integer> results) {
    this.results = results;
  }
}
