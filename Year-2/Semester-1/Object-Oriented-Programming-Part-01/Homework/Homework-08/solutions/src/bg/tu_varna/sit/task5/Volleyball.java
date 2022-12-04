package bg.tu_varna.sit.task5;

public class Volleyball implements PredictionCalculator {

  private final HomeTeam homeTeam;
  private final AwayTeam awayTeam;
  private final boolean lastFameTieBreakHome;
  private final boolean lastFameTieBreakAway;

  public Volleyball(HomeTeam homeTeam, AwayTeam awayTeam, boolean lastFameTieBreakHome, boolean lastFameTieBreakAway) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.lastFameTieBreakHome = lastFameTieBreakHome;
    this.lastFameTieBreakAway = lastFameTieBreakAway;
  }


  @Override
  public String predictWinner() {

    if (lastFameTieBreakHome) {
      if (homeTeam.winProbability() <= 60) {
        return awayTeam.getName();
      } else {
        return homeTeam.getName();
      }
    } else if (lastFameTieBreakAway) {
      if (homeTeam.winProbability() <= 60) {
        return homeTeam.getName();
      } else {
        return awayTeam.getName();
      }
    } else if (homeTeam.winProbability() <= 70) {
      return awayTeam.getName();
    } else {
      return homeTeam.getName();
    }
  }
}
