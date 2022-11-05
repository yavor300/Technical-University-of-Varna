package bg.tu_varna.sit.task2;

class Printer extends Machine {

  private static final double NUMBER_OF_CHARS_PER_PAGE = 3;

  private int numberOfPagesPerMinute;
  private final String content;

  Printer(double price, String content) {
    super(price);
    this.content = content;
  }

  @Override
  Paper[] createTracePaper(int numberOfPapers) {

    int papersCount = (int) Math.ceil(content.length() / NUMBER_OF_CHARS_PER_PAGE);

    Paper[] papers = new Paper[papersCount * numberOfPapers];

    boolean isContentOver = false;
    int lastPapersIndex = 0;
    int beginCharIndex = 0;

    for (int i = 1; i <= papersCount; i++) {

      if (isContentOver) {
        break;
      }

      int validSymbols = 0;

      for (int j = (int) (beginCharIndex++ * NUMBER_OF_CHARS_PER_PAGE); j < NUMBER_OF_CHARS_PER_PAGE * i; j++) {
        try {
          if (content.charAt(j) != '\n' && content.charAt(j) != '\t' && content.charAt(j) != ' ') {
            validSymbols++;
          }
        } catch (IndexOutOfBoundsException e) {
          isContentOver = true;
          break;
        }
      }

      Paper paper = new Paper((validSymbols / NUMBER_OF_CHARS_PER_PAGE) * 100);
      for (int j = 0; j < numberOfPapers; j++) {
        papers[lastPapersIndex++] = paper;
      }

    }

    return papers;
  }
}
