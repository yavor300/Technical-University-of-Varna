package bg.tu_varna.sit.b1.f21621577.base.validator;

import bg.tu_varna.sit.b1.f21621577.base.generator.GeneratedElement;

import java.io.IOException;

public interface Validator<T extends GeneratedElement> {

  boolean isValid(T element) throws IOException;
}
