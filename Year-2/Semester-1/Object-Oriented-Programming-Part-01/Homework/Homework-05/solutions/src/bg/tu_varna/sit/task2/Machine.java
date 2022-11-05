package bg.tu_varna.sit.task2;

abstract class Machine {

  private final double price;

  Machine(double price) {
    this.price = price;
  }

  abstract Paper[] createTracePaper(int numberOfPapers);
}
