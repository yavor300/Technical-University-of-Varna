package bg.tuvarna.sit.dto;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ReportCreateDto {

  private String content;
  private short hoursWorked;
  private Long taskId;
  private Set<TagCreateDto> tags = new HashSet<>();
}
