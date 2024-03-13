package bg.tuvarna.sit.service;

import bg.tuvarna.sit.exceptions.PlayerNotAddedException;
import bg.tuvarna.sit.exceptions.PlayerNotFoundException;
import bg.tuvarna.sit.models.dto.TournamentRequestAddPlayerDto;
import bg.tuvarna.sit.models.dto.TournamentResponsePlayerDto;
import bg.tuvarna.sit.models.dto.TournamentResponseGetAllPlayersDto;
import bg.tuvarna.sit.models.entities.Player;
import bg.tuvarna.sit.repository.TournamentRepository;
import bg.tuvarna.sit.repository.TournamentRepositoryImpl;
import bg.tuvarna.sit.exceptions.PlayerValidationException;
import java.util.Collection;
import java.util.stream.Collectors;

public class TournamentServiceImpl implements TournamentService {

  private final TournamentRepository repository;

  private TournamentServiceImpl(TournamentRepository instance) {
    this.repository = instance;
  }

  private static class SingletonHelper {
    private static final TournamentServiceImpl INSTANCE = new TournamentServiceImpl(TournamentRepositoryImpl.getInstance());
  }

  public static TournamentServiceImpl getInstance() {
    return SingletonHelper.INSTANCE;
  }


  @Override
  public TournamentResponsePlayerDto add(TournamentRequestAddPlayerDto dto) throws PlayerValidationException, PlayerNotAddedException {

    validateTaskDto(dto);
    Player player = convertToEntity(dto);
    addPlayerToRepository(player);

    return convertToReadDto(player);
  }

  @Override
  public TournamentResponseGetAllPlayersDto getAll() {


    Collection<Player> players = repository.getAll();

    return new TournamentResponseGetAllPlayersDto(convertAllToResponseDto(players));
  }

  @Override
  public TournamentResponsePlayerDto getById(String id) throws PlayerValidationException, PlayerNotFoundException {

    long taskId = parseAndValidateId(id);
    Player player = fetchById(taskId);

    return convertToReadDto(player);
  }

  private void validateTaskDto(TournamentRequestAddPlayerDto dto) throws PlayerValidationException {

    if (dto.getElo() == null || dto.getFirstName() == null || dto.getLastName() == null) {
      throw new PlayerValidationException("None of the Player fields (first name, last name, elo ) can be null.");
    }

    if (dto.getElo() <= 0) {
      throw new PlayerValidationException("The elo must be a positive number.");
    }
  }

  private Player convertToEntity(TournamentRequestAddPlayerDto dto) {

    return new Player(dto.getFirstName(), dto.getLastName(), dto.getElo());
  }

  private void addPlayerToRepository(Player player) throws PlayerNotAddedException {

    boolean addedSuccessfully = repository.add(player);
    if (!addedSuccessfully) {
      throw new PlayerNotAddedException("Player could not be added to the repository.");
    }
  }


  private TournamentResponsePlayerDto convertToReadDto(Player player) {

    return new TournamentResponsePlayerDto(
            Long.toString(player.getFideId()),
            player.getFirstName(),
            player.getFamilyName(),
            String.valueOf(player.getElo()));
  }

  private Collection<TournamentResponsePlayerDto> convertAllToResponseDto(Collection<Player> players) {

    return players.stream()
            .map(player -> new TournamentResponsePlayerDto(
                    Long.toString(player.getFideId()),
                    player.getFirstName(),
                    player.getFamilyName(),
                    String.valueOf(player.getElo())))
            .collect(Collectors.toList());
  }

  private long parseAndValidateId(String id) throws PlayerValidationException {

    if (id == null) {
      throw new PlayerValidationException("The player ID cannot be null.");
    }

    try {
      return Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new PlayerValidationException("The player ID is invalid.");
    }
  }

  private Player fetchById(long taskId) throws PlayerNotFoundException {

    Player player = repository.getById(taskId);
    if (player == null) {
      throw new PlayerNotFoundException("Player with ID " + taskId + " not found.");
    }

    return player;
  }

}
