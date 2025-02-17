package bg.tuvarna.sit.dto;

import bg.tuvarna.sit.entities.Role;
import bg.tuvarna.sit.entities.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserCreateDto {

  private Long id;
  private String name;
  private String email;
  private String username;
  private String password;
  private Set<RoleType> roles = new HashSet<>();
}
