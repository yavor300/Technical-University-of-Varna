package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskResponseDto {

  @XmlElement
  private String id;
  @XmlElement
  private String heading;
  @XmlElement
  private String description;
  @XmlElement
  private String deadline;

  public TaskResponseDto() {
  }

  public TaskResponseDto(String id, String heading, String description, String deadline) {
    this.id = id;
    this.heading = heading;
    this.description = description;
    this.deadline = deadline;
  }

  public String getId() {
    return id;
  }

  public String getHeading() {
    return heading;
  }

  public String getDescription() {
    return description;
  }

  public String getDeadline() {
    return deadline;
  }
}
