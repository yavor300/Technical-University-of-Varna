package bg.tuvarna.sit.context;

import bg.tuvarna.sit.service.TournamentService;
import bg.tuvarna.sit.service.TournamentServiceImpl;

public class ApplicationContext {

  private static final TournamentService TOURNAMENT_SERVICE = TournamentServiceImpl.getInstance();

  public static TournamentService getTournamentService() {
    return TOURNAMENT_SERVICE;
  }
}
