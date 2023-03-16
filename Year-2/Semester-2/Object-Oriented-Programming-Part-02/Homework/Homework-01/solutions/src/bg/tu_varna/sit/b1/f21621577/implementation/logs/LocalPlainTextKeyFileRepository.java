package bg.tu_varna.sit.b1.f21621577.implementation.logs;

import bg.tu_varna.sit.b1.f21621577.base.logger.FileRepository;

import java.io.IOException;
import java.io.Writer;

public class LocalPlainTextKeyFileRepository extends FileRepository {

  public LocalPlainTextKeyFileRepository(Writer writer) {
    super(writer);
  }

  @Override
  public void save(String data) throws IOException {

    getWriter().append(data).append('\n');
    getWriter().close();
  }
}
