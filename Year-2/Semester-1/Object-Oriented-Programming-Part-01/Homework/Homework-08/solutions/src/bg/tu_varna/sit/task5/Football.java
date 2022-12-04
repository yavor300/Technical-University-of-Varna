package bg.tu_varna.sit.task5;

public class Football implements PredictionCalculator {

  private final HomeTeam homeTeam;
  private final AwayTeam awayTeam;

  public Football(HomeTeam homeTeam, AwayTeam awayTeam) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
  }

  @Override
  public String predictWinner() {

    if (homeTeam.winProbability() > 80) {
      return homeTeam.getName();
    } else if (homeTeam.winProbability() > 50) {
      return "draw";
    }  else  {
      return awayTeam.getName();
    }
  }
}
