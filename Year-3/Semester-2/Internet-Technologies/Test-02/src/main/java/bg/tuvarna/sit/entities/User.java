package bg.tuvarna.sit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 60, nullable = false)
  private String name;
  @Column(name = "email", length = 60, nullable = false, unique = true)
  private String email;
  @Column(name = "username", length = 60, nullable = false, unique = true)

  private String username;
  @Column(name = "password", length = 60, nullable = false)
  private String password;

  @ManyToMany
  private Set<Role> roles = new HashSet<>();
}
