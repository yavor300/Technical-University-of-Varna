package bg.tu_varna.sit.task07;

public class BitShifter {
  private final String[] rotations;

  public BitShifter(String[] rotations) {
    this.rotations = rotations;
  }

  public byte shift(byte number) {
    for (String direction : rotations) {
      if (direction.equals("right")) {
        for (int i = 0; i < 6; i++) {
          int rightMostBit = number & 1;
          if (number < 0 && rightMostBit == 0) {
            number >>= 1;
            number = (byte) (number & ~(1 << 7));
            continue;
          }
          number >>= 1;
          number |= rightMostBit << 7;
        }
      } else if (direction.equals("left")) {
        for (int i = 0; i < 6; i++) {
          int leftMostBit = (number >> 7) & 1;
          number <<= 1;
          number |= leftMostBit;
        }
      }

    }
    return number;
  }
}
