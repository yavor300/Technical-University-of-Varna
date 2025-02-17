package bg.tuvarna.sit.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ReportEditDto {

  private Long id;
  private String content;
  private short hoursWorked;
  private LocalDateTime dateCreated;
  private LocalDateTime dateUpdated;
  private Set<TagCreateDto> tags = new HashSet<>();
}
