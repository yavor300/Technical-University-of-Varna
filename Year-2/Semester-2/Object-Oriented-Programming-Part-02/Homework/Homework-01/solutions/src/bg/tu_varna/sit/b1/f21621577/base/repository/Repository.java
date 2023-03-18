package bg.tu_varna.sit.b1.f21621577.base.repository;

import bg.tu_varna.sit.b1.f21621577.base.generator.GeneratedElement;

import java.io.IOException;

public interface Repository<T extends GeneratedElement> {

  void save(T element) throws IOException;
}
