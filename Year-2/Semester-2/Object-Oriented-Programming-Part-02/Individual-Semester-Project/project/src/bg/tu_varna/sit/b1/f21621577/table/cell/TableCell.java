package bg.tu_varna.sit.b1.f21621577.table.cell;

public class TableCell {

  private String data;
  private final boolean isDigit;
  private int number;

  public TableCell(String data) {
    this.data = data;
    this.isDigit = false;
  }

  public TableCell(int number) {
    this.number = number;
    this.isDigit = true;
  }
}
