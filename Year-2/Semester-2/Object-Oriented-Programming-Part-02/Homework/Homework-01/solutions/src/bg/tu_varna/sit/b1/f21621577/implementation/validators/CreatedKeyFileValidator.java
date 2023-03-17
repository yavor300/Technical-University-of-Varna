//package bg.tu_varna.sit.b1.f21621577.implementation.validators;
//
//import bg.tu_varna.sit.b1.f21621577.base.logger.FileRepository;
//import bg.tu_varna.sit.b1.f21621577.base.validator.Validator;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//public class CreatedKeyFileValidator implements Validator {
//
//  private final FileRepository fileLogger;
//  private final BufferedReader inputReader;
//
//  public CreatedKeyFileValidator(FileRepository fileLogger) throws FileNotFoundException {
//    this.fileLogger = fileLogger;
//    this.inputReader = new BufferedReader(new FileReader(fileLogger.get()));
//  }
//
//  @Override
//  public boolean isValid(String data) throws IOException {
//
//    String nextLine;
//    while ((nextLine = inputReader.readLine()) != null){
//      if (nextLine.trim().equals(data)) {
//        return true;
//      }
//    }
//    return false;
//  }
//
//}
