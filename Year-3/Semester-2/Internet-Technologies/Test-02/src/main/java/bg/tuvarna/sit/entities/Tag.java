package bg.tuvarna.sit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "heading", nullable = false, length = 30, unique = true)
  private String heading;

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @ManyToMany(mappedBy = "tags")
  private Set<Report> reports = new HashSet<>();
}
