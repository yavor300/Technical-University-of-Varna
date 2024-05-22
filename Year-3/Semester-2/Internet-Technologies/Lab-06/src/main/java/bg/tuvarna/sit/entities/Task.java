package bg.tuvarna.sit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "summary", length = 150, nullable = false)
  private String summary;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "deadline", length = 50, nullable = false)
  private LocalDateTime deadline;

  @OneToMany(mappedBy = "task")
  private Set<Report> reports = new HashSet<>();
}
