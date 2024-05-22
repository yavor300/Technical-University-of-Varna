package bg.tuvarna.sit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorDto {

  private LocalDateTime time;
  private String message;
}
