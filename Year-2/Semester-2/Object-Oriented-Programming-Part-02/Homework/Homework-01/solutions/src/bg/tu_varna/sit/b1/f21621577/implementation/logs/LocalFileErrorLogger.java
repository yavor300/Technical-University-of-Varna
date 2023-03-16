//package bg.tu_varna.sit.b1.f21621577.implementation.logs;
//
//import bg.tu_varna.sit.b1.f21621577.base.logger.FileRepository;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class LocalFileErrorLogger extends FileRepository {
//
//  public LocalFileErrorLogger(String name) throws IOException {
//    super(name);
//  }
//
//  public LocalFileErrorLogger(String directory, String name) throws IOException {
//    super(directory, name);
//  }
//
//  @Override
//  public void log(String data) {
//
//    PrintWriter printWriter = new PrintWriter(getFileWriter());
//    printWriter.println(data);
//    printWriter.close();
//  }
//}
