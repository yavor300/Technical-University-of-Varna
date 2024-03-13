package bg.tuvarna.sit.repository;

import bg.tuvarna.sit.models.entities.Player;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TournamentRepositoryImpl implements TournamentRepository {

  private final Collection<Player> players;
  private final AtomicLong idGenerator;

  private TournamentRepositoryImpl() {

    this.players = new HashSet<>();
    this.idGenerator = new AtomicLong(0);
  }

  private static class SingletonHelper {
    private static final TournamentRepositoryImpl INSTANCE = new TournamentRepositoryImpl();
  }

  public static TournamentRepositoryImpl getInstance() {

    return SingletonHelper.INSTANCE;
  }


  @Override
  public boolean add(Player player) {

    player.setFideId(idGenerator.incrementAndGet());
    return players.add(player);
  }

  @Override
  public Collection<Player> getAll() {

    return players.stream()
            .sorted()
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Player getById(long id) {

    return players.stream()
            .filter(player -> player.getFideId() == id)
            .findFirst()
            .orElse(null);
  }
}
