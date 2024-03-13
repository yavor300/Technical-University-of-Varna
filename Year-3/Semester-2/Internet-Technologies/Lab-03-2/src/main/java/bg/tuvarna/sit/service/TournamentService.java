package bg.tuvarna.sit.service;

import bg.tuvarna.sit.exceptions.PlayerNotAddedException;
import bg.tuvarna.sit.exceptions.PlayerNotFoundException;
import bg.tuvarna.sit.exceptions.PlayerValidationException;
import bg.tuvarna.sit.models.dto.TournamentRequestAddPlayerDto;
import bg.tuvarna.sit.models.dto.TournamentResponsePlayerDto;
import bg.tuvarna.sit.models.dto.TournamentResponseGetAllPlayersDto;

public interface TournamentService {

  TournamentResponsePlayerDto add(TournamentRequestAddPlayerDto dto) throws PlayerValidationException, PlayerNotAddedException;

  TournamentResponseGetAllPlayersDto getAll();

  TournamentResponsePlayerDto getById(String id) throws PlayerValidationException, PlayerNotFoundException;
}
