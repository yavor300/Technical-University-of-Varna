package tuvarna.exercise05;

public class IdentificationCard {

  private final String number;
  private final int year;
  private final String egn;
  private final City city;

  public IdentificationCard(String number, int year, String egn, City city) {
    this.number = number;
    this.year = year;
    this.egn = egn;
    this.city = city;
  }

  @Override
  public String toString() {
    return String.format("Лична карта номер: %s Издадена от: %s",
            number, publishedBy());
  }

  private String publishedBy() {
    return String.format("МВР %s", city.getName());
  }

  public int validUntil() {
    return year + 10;
  }

  public String getNumber() {
    return number;
  }

  public String getEgn() {
    return egn;
  }
}
