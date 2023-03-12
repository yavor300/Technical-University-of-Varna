package bg.tu_varna.sit.b1.f21621577.implementation.keys;

import bg.tu_varna.sit.b1.f21621577.base.generator.KeyGenerator;

import java.io.IOException;

public class DigitLetterKeyGenerator extends KeyGenerator {

  public DigitLetterKeyGenerator() {
  }

  public DigitLetterKeyGenerator(int length) {
    super(length);
  }

  @Override
  public String generate() {

    getStringBuilder().setLength(0);

    for (int i = 0; i < getLength(); i++) {
      int charCode;

      do {
        charCode = (int) '0' +
                (int) (getRandom().nextFloat() * ((int) 'z' - (int) '0' + 1));
      } while ((charCode > (int) '9' && charCode < (int) 'A')
              || (charCode > (int) 'Z' && charCode < (int) 'a'));

      getStringBuilder().append((char) charCode);
    }

    return getStringBuilder().toString();
  }
}
