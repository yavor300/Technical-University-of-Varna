package bg.tu_varna.sit.b1.f21621577.implementation.key.generator;

import bg.tu_varna.sit.b1.f21621577.base.generator.KeyGenerator;
import bg.tu_varna.sit.b1.f21621577.implementation.key.type.LetterDigitKey;

import java.util.Random;

public class DigitLetterKeyGenerator extends KeyGenerator<LetterDigitKey> {

  public DigitLetterKeyGenerator(Random random, StringBuilder stringBuilder) {
    super(random, stringBuilder);
  }

  public DigitLetterKeyGenerator(int length, Random random, StringBuilder stringBuilder) {
    super(length, random, stringBuilder);
  }

  @Override
  public LetterDigitKey generate() {

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

    return new LetterDigitKey(getStringBuilder().toString());
  }
}
