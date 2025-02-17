package bg.tuvarna.sit.entities;

import jakarta.persistence.*;
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
  @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
  )
  private Set<Role> roles = new HashSet<>();
}
