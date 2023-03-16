package bg.tu_varna.sit.b1.f21621577.implementation.keys;

import bg.tu_varna.sit.b1.f21621577.base.generator.KeyGenerator;

import java.util.Random;

public class DigitKeyGenerator extends KeyGenerator {

  public DigitKeyGenerator(Random random, StringBuilder stringBuilder) {
    super(random, stringBuilder);
  }

  public DigitKeyGenerator(int length, Random random, StringBuilder stringBuilder) {
    super(length, random, stringBuilder);
  }

  @Override
  public String generate() {

    getStringBuilder().setLength(0);

    for (int i = 0; i < getLength(); i++) {

      int charCode = (int) '0' +
              (int) (getRandom().nextFloat() * ((int) '9' - (int) '0' + 1));

      getStringBuilder().append((char) charCode);
    }

    return getStringBuilder().toString();
  }
}
