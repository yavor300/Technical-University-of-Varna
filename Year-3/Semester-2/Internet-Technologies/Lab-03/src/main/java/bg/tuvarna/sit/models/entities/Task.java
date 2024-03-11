package bg.tuvarna.sit.models.entities;

import java.time.LocalDateTime;

public class Task implements Comparable<Task> {

  private Long id;
  private String heading;
  private String description;
  private LocalDateTime deadline;

  public Task(String heading, String description, LocalDateTime deadline) {
    this.heading = heading;
    this.description = description;
    this.deadline = deadline;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Task task = (Task) o;

    return id.equals(task.id);
  }

  @Override
  public int hashCode() {

    return id.hashCode();
  }

  @Override
  public int compareTo(Task o) {

    // Descending
    // return other.id.compareTo(this.id);
    return this.id.compareTo(o.getId());

  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
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

  public void setHeading(String heading) {
    this.heading = heading;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDeadline(LocalDateTime deadline) {
    this.deadline = deadline;
  }
}
