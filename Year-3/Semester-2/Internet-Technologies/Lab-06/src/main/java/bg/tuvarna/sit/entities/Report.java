package bg.tuvarna.sit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report implements Comparable<Report> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "hours_worked", nullable = false)
  private short hoursWorked;

  @Column(name = "date_created", length = 50, nullable = false)
  @CreationTimestamp
  private LocalDateTime dateCreated;

  @Column(name = "date_updated", length = 50, nullable = false)
  @UpdateTimestamp
  private LocalDateTime dateUpdated;

  @ManyToOne
  @JoinColumn(name = "task_id", nullable = false)
  private Task task;

  @Override
  public int compareTo(Report other) {
    return Short.compare(this.hoursWorked, other.hoursWorked);
  }
}
