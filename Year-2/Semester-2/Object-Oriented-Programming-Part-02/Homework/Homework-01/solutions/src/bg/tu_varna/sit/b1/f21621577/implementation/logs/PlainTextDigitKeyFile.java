package bg.tu_varna.sit.b1.f21621577.implementation.logs;

import bg.tu_varna.sit.b1.f21621577.implementation.keys.types.DigitKey;
import bg.tu_varna.sit.b1.f21621577.base.logger.FileRepository;

import java.io.IOException;
import java.io.Writer;

public class PlainTextDigitKeyFile extends FileRepository<DigitKey> {

  public PlainTextDigitKeyFile(Writer writer) {
    super(writer);
  }

  @Override
  public void save(DigitKey element) throws IOException {

    getWriter().append(element.getData()).append('\n');
    getWriter().close();
  }
}
