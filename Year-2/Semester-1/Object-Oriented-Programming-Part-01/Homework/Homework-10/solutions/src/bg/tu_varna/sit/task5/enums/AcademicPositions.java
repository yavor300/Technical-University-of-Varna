package bg.tu_varna.sit.task5.enums;

public enum AcademicPositions {

  Assistant("ас."),
  ChiefAssistant("гл. ас."),
  Lecturer("пр"),
  SeniorLecturer("ст. пр."),
  AssociateProfessor("доц."),
  Professor("проф.");

  public final String abbreviation;

  private AcademicPositions(String abbreviation) {
    this.abbreviation = abbreviation;
  }


}
