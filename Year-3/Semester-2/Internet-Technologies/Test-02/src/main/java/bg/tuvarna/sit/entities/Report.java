package bg.tuvarna.sit.entities;

import bg.tuvarna.sit.dto.TaskDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {

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

  @ManyToMany
  @JoinTable(
          name = "reports_tags",
          joinColumns = @JoinColumn(name = "report_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
  )
  private Set<Tag> tags = new HashSet<>();
}
