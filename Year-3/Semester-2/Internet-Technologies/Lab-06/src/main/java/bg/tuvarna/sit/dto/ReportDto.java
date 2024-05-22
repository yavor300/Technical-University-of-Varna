package bg.tuvarna.sit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportDto {

  private Long number;
  private String content;
  private short hoursWorked;
  private LocalDateTime dateCreated;
  private LocalDateTime dateUpdated;
  private TaskDto task;


}
