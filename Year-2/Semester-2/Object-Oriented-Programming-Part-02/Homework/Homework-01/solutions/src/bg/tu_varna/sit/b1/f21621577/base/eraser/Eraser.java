package bg.tu_varna.sit.b1.f21621577.base.eraser;

import bg.tu_varna.sit.b1.f21621577.base.generator.GeneratedElement;

import java.io.IOException;

public interface Eraser<T extends GeneratedElement> {

  void erase(T element) throws IOException;
}
