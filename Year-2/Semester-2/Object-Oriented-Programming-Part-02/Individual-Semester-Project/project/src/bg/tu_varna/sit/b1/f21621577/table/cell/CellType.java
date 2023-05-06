package bg.tu_varna.sit.b1.f21621577.table.cell;

/**
 * An enumeration representing the possible types of a table cell.
 * <p>
 * Изброяване, представящо възможните типове клетка от таблица.
 * <ul>
 * <li> {@link #INTEGER} - an integer number; цяло число
 * <li> {@link #FRACTIONAL} - a fractional number; дробно число
 * <li> {@link #STRING} - a string; низ
 * <li> {@link #FORMULA} - a formula; формула
 * <li> {@link #EMPTY} - an empty cell; празна клетка
 * </ul>
 */
public enum CellType {
  INTEGER,
  FRACTIONAL,
  STRING,
  FORMULA,
  EMPTY
}
