package bg.tu_varna.sit.task2;

public class PaperDocument {

  private final String title;
  private final String dateCreated;
  private int pages;
  private Access access;

  public PaperDocument(String title, String dateCreated, int pages, Access access) {
    this.title = title;
    this.dateCreated = dateCreated;
    this.pages = pages;
    this.access = access;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PaperDocument that = (PaperDocument) o;

    if (!title.equals(that.title)) return false;
    return dateCreated.equals(that.dateCreated);
  }

  @Override
  public int hashCode() {
    int result = title.hashCode();
    result = 31 * result + dateCreated.hashCode();
    return result;
  }
}
