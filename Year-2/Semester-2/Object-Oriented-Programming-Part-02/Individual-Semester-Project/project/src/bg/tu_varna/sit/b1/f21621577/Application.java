package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.table.reader.TableReader;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;
import bg.tu_varna.sit.b1.f21621577.table.writer.TableWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_RESOURCES_DIRECTORY;

public class Application {

  public static void main(String[] args) {

    File file = new File(DEFAULT_RESOURCES_DIRECTORY + "data.txt");
    TableReader tableReader;
    try {
       tableReader = new TableReader(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    String[][] cells;
    try {
       cells = tableReader.read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    TableWriter tableWriter = new TableWriter();
    tableWriter.write(cells);

    System.out.println(TableRepository.getInstance().visualizeData());
  }
}
