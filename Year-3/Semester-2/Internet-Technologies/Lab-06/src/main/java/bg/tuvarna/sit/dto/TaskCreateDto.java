package bg.tuvarna.sit.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskCreateDto {

  private String summary;
  private String description;
  private LocalDateTime deadline;
}
