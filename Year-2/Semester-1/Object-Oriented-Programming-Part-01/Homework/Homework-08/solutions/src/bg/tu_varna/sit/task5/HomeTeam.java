package bg.tu_varna.sit.task5;

public class HomeTeam extends Team {

  public HomeTeam(String name, TeamRank teamRank, LastGameResult lastGameResult) {
    super(name, teamRank, lastGameResult);
  }

  @Override
  public double winProbability() {

    switch (getTeamRank()) {

      case TOP_TEAM:
        if (getLastGameResult() == LastGameResult.WIN) {
          return 90;
        } else if (getLastGameResult() == LastGameResult.DRAW) {
          return 65;
        } else {
          return 55;
        }

      case AVERAGE_TEAM:
        if (getLastGameResult() == LastGameResult.WIN) {
          return 70;
        } else if (getLastGameResult() == LastGameResult.DRAW) {
          return 48;
        } else {
          return 35;
        }

      default:
        if (getLastGameResult() == LastGameResult.WIN) {
          return 60;
        } else if (getLastGameResult() == LastGameResult.DRAW) {
          return 40;
        } else {
          return 20;
        }
    }
  }
}
