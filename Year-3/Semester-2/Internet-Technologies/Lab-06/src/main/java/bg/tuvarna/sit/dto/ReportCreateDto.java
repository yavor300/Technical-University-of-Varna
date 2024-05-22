package bg.tuvarna.sit.dto;

import lombok.Getter;

@Getter
public class ReportCreateDto {

  private String content;
  private short hoursWorked;
  private Long taskId;
}
