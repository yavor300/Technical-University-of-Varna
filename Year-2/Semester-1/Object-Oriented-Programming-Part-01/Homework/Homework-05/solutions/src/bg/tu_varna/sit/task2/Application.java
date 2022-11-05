package bg.tu_varna.sit.task2;

class Application {

  public static void main(String[] args) {

    Machine printer = new Printer(2, "aaaaaaaaaa");
    Paper[] tracePaper = printer.createTracePaper(3);

    Duplicator duplicator = new Duplicator(2, 2, new Paper(80));
    Paper[] tracePaper1 = duplicator.createTracePaper(3);
  }
}
