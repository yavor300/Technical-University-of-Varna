package bg.tu_varna.sit.task5;

public class AwayTeam extends Team {

  public AwayTeam(String name, TeamRank teamRank, LastGameResult lastGameResult) {
    super(name, teamRank, lastGameResult);
  }

  @Override
  public double winProbability() {

    switch (getTeamRank()) {

      case TOP_TEAM:
        if (getLastGameResult() == LastGameResult.WIN) {
          return 80;
        } else if (getLastGameResult() == LastGameResult.DRAW) {
          return 60;
        } else {
          return 45;
        }

      case AVERAGE_TEAM:
        if (getLastGameResult() == LastGameResult.WIN) {
          return 60;
        } else if (getLastGameResult() == LastGameResult.DRAW) {
          return 40;
        } else {
          return 30;
        }

      default:
        if (getLastGameResult() == LastGameResult.WIN) {
          return 55;
        } else if (getLastGameResult() == LastGameResult.DRAW) {
          return 38;
        } else {
          return 15;
        }
    }
  }
}
