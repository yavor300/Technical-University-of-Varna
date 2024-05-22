package bg.tuvarna.sit.entities;

public enum RoleType {
  ADMIN,
  USER;

  public Long getValue() {
    return ordinal() + 1L;
  }
}
