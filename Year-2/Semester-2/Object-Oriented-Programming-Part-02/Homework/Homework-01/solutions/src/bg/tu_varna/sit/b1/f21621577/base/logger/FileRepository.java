package bg.tu_varna.sit.b1.f21621577.base.logger;

import bg.tu_varna.sit.b1.f21621577.base.generator.GeneratedElement;

import java.io.Writer;

public abstract class FileRepository<T extends GeneratedElement> implements Repository<T> {

  private final Writer writer;

  protected FileRepository(Writer writer) {
    this.writer = writer;
  }

  protected Writer getWriter() {
    return writer;
  }
}
