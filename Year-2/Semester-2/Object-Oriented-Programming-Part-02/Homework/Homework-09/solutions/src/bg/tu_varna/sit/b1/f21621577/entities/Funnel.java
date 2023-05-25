package bg.tu_varna.sit.b1.f21621577.entities;

import java.util.ArrayList;
import java.util.List;

public class Funnel {

  private final List<String> flavors;

  public Funnel() {
    flavors = new ArrayList<>();
  }

  public void addFlavor(String flavor) {
    flavors.add(flavor);
  }

  public List<String> getFlavors() {
    return flavors;
  }
}
