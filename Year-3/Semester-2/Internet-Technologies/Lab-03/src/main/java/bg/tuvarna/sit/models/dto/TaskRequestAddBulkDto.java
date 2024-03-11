package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskRequestAddBulkDto {

  @XmlElement(name = "task")
  private List<TaskRequestAddDto> tasks;

  public TaskRequestAddBulkDto() {
  }

  public List<TaskRequestAddDto> getTasks() {
    return tasks;
  }
}
