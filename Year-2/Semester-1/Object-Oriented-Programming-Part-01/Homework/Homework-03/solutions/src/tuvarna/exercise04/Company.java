package tuvarna.exercise04;

import java.time.LocalDate;

public class Company {

  private static final String INVALID_ID_MESSAGE = "Invalid company Id provided.";

  private final String name;
  private final LocalDate created;
  private String id;

  protected Company(String name, LocalDate created, String id) {
    this.name = name;
    this.created = created;
    setId(id);
  }

  @Override
  public String toString() {
    return String.format("Company type: %s%nName: %s%nCreated: %s%nId: %s",
            getClass().getSimpleName(), getName(), getCreated(), getId());
  }

  protected String getName() {
    return name;
  }

  protected LocalDate getCreated() {
    return created;
  }

  protected String getId() {
    return id;
  }

  private void setId(String id) {

    if (id.length() != 10) {
      throw new IllegalArgumentException(INVALID_ID_MESSAGE);
    }

    for (char c : id.toCharArray()) {
      if (!Character.isLetter(c) && !Character.isDigit(c)) {
        throw new IllegalArgumentException(INVALID_ID_MESSAGE);
      }
    }

    this.id = id;
  }
}
