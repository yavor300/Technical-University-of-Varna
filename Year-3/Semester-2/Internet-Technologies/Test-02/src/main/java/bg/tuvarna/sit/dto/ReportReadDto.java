package bg.tuvarna.sit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ReportReadDto {

  private Long number;
  private String content;
  private short hoursWorked;
  private LocalDateTime dateCreated;
  private LocalDateTime dateUpdated;
  private Set<TagReadDto> tags = new HashSet<>();
}
