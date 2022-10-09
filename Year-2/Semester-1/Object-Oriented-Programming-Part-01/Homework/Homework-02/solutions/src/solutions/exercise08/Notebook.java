package exercise08;

public class Notebook {
  private Integer pages;
  private Double price;
  private Boolean isWithHardCovers;

  public Notebook(Integer pages, Double price, Boolean isWithHardCovers) {
    this.pages = pages;
    this.price = price;
    this.isWithHardCovers = isWithHardCovers;
  }

  @Override
  public String toString() {
    return String.format("Pages: %d%nPrice: %.2f BGN%nHard covers: %s",
            getPages(), getPrice(), getWithHardCovers() ? "Yes" : "No");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Notebook notebook = (Notebook) o;

    if (!getPages().equals(notebook.getPages())) return false;
    if (!getPrice().equals(notebook.getPrice())) return false;
    return getWithHardCovers().equals(notebook.getWithHardCovers());
  }

  public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Boolean getWithHardCovers() {
    return isWithHardCovers;
  }

  public void setWithHardCovers(Boolean withHardCovers) {
    isWithHardCovers = withHardCovers;
  }
}
