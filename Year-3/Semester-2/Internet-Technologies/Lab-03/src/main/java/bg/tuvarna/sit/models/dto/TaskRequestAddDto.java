package bg.tuvarna.sit.models.dto;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskRequestAddDto {

  @XmlElement(name = "heading")
  private String heading;

  @XmlElement(name = "description")
  private String description;

  @XmlElement(name = "deadline")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  private LocalDateTime deadline;

  public TaskRequestAddDto() {
  }

  public String getHeading() {
    return heading;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  /**
   * We will receive data and time in format: 2024-05-04T12:12.
   * Only parsing is required.
   * We can skip specifying any formatters.
   */
  public static class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    public LocalDateTime unmarshal(String v) {
      return LocalDateTime.parse(v);
    }

    public String marshal(LocalDateTime v) {
      return v.toString();
    }
  }
}
