package bg.tu_varna.sit.task5;

public class MatchPrediction<T extends PredictionCalculator> {

  private final T match;

  public MatchPrediction(T match) {
    this.match = match;
  }

  public void displayPredictedWinner() {

    System.out.println(match.predictWinner());
  }
}
