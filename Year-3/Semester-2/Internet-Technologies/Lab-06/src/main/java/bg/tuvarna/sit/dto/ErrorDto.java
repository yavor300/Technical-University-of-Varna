package bg.tuvarna.sit.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDto {

  private LocalDateTime time;

  private List<String> errors;

  public void addError(String error) {
    errors.add(error);
  }
}
