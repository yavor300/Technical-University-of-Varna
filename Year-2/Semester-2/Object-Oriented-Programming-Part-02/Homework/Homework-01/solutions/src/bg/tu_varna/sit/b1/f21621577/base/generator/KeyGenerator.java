package bg.tu_varna.sit.b1.f21621577.base.generator;

import java.util.Random;

public abstract class KeyGenerator implements Generator {

  private int length = 6;
  private final Random random;
  private final StringBuilder stringBuilder;

  protected KeyGenerator(Random random, StringBuilder stringBuilder) {
    this.random = random;
    this.stringBuilder = stringBuilder;
  }

  protected KeyGenerator(int length, Random random, StringBuilder stringBuilder) {
    this(random, stringBuilder);
    this.length = length;
  }

  protected int getLength() {
    return length;
  }

  protected Random getRandom() {
    return random;
  }

  protected StringBuilder getStringBuilder() {
    return stringBuilder;
  }
}
