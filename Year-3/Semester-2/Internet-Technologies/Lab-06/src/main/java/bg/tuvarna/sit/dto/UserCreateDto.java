package bg.tuvarna.sit.dto;

import bg.tuvarna.sit.entities.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

  @NotBlank(message = "Name is required and cannot be blank")
  private String name;

  @NotBlank(message = "Email is required and cannot be blank")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Username is required and cannot be blank")
  private String username;

  @NotBlank(message = "Password is required and cannot be blank")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;

  @NotNull(message = "Roles set cannot be null")
  @Size(min = 1, message = "At least one role is required")
  private Set<RoleType> roles = new HashSet<>();
}
