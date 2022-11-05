package bg.tu_varna.sit.task2;

class Duplicator extends Machine {

  private final int paperCount;
  private final Paper paper;

  Duplicator(double price, int paperCount, Paper paper) {
    super(price);
    this.paperCount = paperCount;
    this.paper = paper;
  }

  @Override
  Paper[] createTracePaper(int numberOfPapers) {

    if (paperCount <= 0) {
      return new Paper[0];
    }

    Paper[] papers = new Paper[numberOfPapers];

    for (int i = 0; i < numberOfPapers; i++) {
      try {
        papers[i] = (Paper) paper.clone();
      } catch (CloneNotSupportedException e) {
        System.out.println("Clone method failed!");
        e.printStackTrace();
      }
    }

    return papers;
  }
}
