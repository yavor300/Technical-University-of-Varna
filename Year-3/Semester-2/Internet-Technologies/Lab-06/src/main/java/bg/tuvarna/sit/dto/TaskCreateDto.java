package bg.tuvarna.sit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TaskCreateDto {

  @NotBlank(message = "Summary is required and cannot be blank")
  private String summary;

  @NotBlank(message = "Description is required and cannot be blank")
  @Size(min = 10, message = "Description must be at least 10 characters long")
  private String description;

  @NotNull(message = "Deadline is required and cannot be null")
  private LocalDateTime deadline;
}

