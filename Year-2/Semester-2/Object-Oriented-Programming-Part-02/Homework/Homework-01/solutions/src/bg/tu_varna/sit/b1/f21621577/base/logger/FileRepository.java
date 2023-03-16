package bg.tu_varna.sit.b1.f21621577.base.logger;

import java.io.Writer;

public abstract class FileRepository implements Repository {

  private final Writer writer;

  protected FileRepository(Writer writer) {
    this.writer = writer;
  }

  protected Writer getWriter() {
    return writer;
  }
}
