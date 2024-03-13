package bg.tuvarna.sit.repository;

import bg.tuvarna.sit.models.entities.Player;
import java.util.Collection;

public interface TournamentRepository {

  boolean add(Player player);

  Collection<Player> getAll();

  Player getById(long id);
}
