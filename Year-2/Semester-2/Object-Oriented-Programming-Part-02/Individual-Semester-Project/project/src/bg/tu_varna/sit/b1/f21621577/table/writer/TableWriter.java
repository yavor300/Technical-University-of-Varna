package bg.tu_varna.sit.b1.f21621577.table.writer;

import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

/**
 * Writer class used to save the formatted and validated data coming
 * from the reader.
 */
public class TableWriter {

  private final TableRepository tableRepository = TableRepository.getInstance();

  public void write(String[][] data) {

    tableRepository.save(data);
  }
}

