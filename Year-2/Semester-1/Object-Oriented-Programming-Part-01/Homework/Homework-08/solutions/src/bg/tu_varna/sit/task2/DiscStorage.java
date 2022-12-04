package bg.tu_varna.sit.task2;

public class DiscStorage {

  private int id;
  private String content;

  public DiscStorage(int id, String content) {
    this.id = id;
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DiscStorage that = (DiscStorage) o;

    return id == that.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
