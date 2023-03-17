package bg.tu_varna.sit.b1.f21621577.implementation.logs;

import bg.tu_varna.sit.b1.f21621577.base.logger.FileRepository;
import bg.tu_varna.sit.b1.f21621577.implementation.keys.types.LetterDigitKey;

import java.io.IOException;
import java.io.Writer;

public class PlainTextLetterDigitKeyFile extends FileRepository<LetterDigitKey> {

  public PlainTextLetterDigitKeyFile(Writer writer) {
    super(writer);
  }

  @Override
  public void save(LetterDigitKey element) throws IOException {

    getWriter().append(element.getData()).append('\n');
    getWriter().close();
  }
}
