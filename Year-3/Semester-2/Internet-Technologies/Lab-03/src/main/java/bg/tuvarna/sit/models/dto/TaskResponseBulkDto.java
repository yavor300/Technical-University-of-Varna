package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskResponseBulkDto {

  @XmlElement(name = "task")
  private Collection<TaskResponseDto> tasks;

  public TaskResponseBulkDto() {
  }

  public TaskResponseBulkDto(Collection<TaskResponseDto> tasks) {
    this.tasks = tasks;
  }
}
