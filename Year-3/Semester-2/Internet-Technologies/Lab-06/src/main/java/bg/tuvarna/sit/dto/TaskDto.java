package bg.tuvarna.sit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto extends TaskCreateDto {

  private Long number;
  private String summary;
  private String description;
  private LocalDateTime deadline;
  private Set<ReportDto> reports;
}
