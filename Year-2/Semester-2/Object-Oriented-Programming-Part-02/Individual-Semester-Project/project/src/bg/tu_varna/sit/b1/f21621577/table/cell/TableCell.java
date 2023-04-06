package bg.tu_varna.sit.b1.f21621577.table.cell;

public class TableCell {

  private CellType type;
  private Object value;

  public TableCell(CellType type, Object value) {
    this.type = type;
    this.value = value;
  }

  public CellType getType() {
    return type;
  }

  public void setType(CellType type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}
