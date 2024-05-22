package bg.tuvarna.sit.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

  private Long number;
  private String summary;
  private String description;
  private LocalDateTime deadline;
  private Set<ReportDto> reports = new HashSet<>();
}
