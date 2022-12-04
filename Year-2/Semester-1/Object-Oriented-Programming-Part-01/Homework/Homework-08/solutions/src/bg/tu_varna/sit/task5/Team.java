package bg.tu_varna.sit.task5;

public abstract class Team implements Results {

  private final String name;
  private final TeamRank teamRank;
  private final LastGameResult lastGameResult;

  protected Team(String name, TeamRank teamRank, LastGameResult lastGameResult) {
    this.name = name;
    this.teamRank = teamRank;
    this.lastGameResult = lastGameResult;
  }

  protected String getName() {
    return name;
  }

  protected TeamRank getTeamRank() {
    return teamRank;
  }

  protected LastGameResult getLastGameResult() {
    return lastGameResult;
  }
}
