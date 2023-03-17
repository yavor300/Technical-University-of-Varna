package bg.tu_varna.sit.b1.f21621577.implementation.logs;

import bg.tu_varna.sit.b1.f21621577.base.logger.FileRepository;
import bg.tu_varna.sit.b1.f21621577.implementation.errors.KeyError;

import java.io.IOException;
import java.io.Writer;

public class PlainTextErrorKeyFile extends FileRepository<KeyError> {

  public PlainTextErrorKeyFile(Writer writer) {
    super(writer);
  }

  @Override
  public void save(KeyError element) throws IOException {

    getWriter().append(element.getData()).append('\n');
    getWriter().close();
  }
}
