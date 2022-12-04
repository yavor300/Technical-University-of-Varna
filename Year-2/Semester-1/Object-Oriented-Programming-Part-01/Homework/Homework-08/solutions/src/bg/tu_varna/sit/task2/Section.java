package bg.tu_varna.sit.task2;

public class Section<E> {

  private final String title;
  private final E[] documents;

  public Section(String title, E[] documents) {
    this.title = title;
    this.documents = documents;
  }

  public String findDocument(E document) {

    for (E doc : documents) {
      if (doc.equals(document)) {
        return "Открит";
      }
    }

    return "Не е открит";
  }
}
