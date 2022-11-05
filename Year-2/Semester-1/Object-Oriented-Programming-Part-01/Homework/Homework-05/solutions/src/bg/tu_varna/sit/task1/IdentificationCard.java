package bg.tu_varna.sit.task1;

public class IdentificationCard {

  private final String number;
  private final int year;
  private String egn;
  private final City city;

  IdentificationCard(String number, int year, String egn, City city) {
    this.number = number;
    this.year = year;
    this.egn = egn;
    this.city = city;
  }

  @Override
  public String toString() {

    return String.format("Лична карта номер: %s%nИздадена от: %s",
            number, publishedBy());
  }

  private String publishedBy() {

    return String.format("МВР %s", city.getName());
  }

  private int validUntil() {

    return year + 10;
  }
}
