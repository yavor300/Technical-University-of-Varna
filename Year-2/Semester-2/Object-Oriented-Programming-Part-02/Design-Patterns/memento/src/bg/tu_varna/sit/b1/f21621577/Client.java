package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.entities.Memento;
import bg.tu_varna.sit.b1.f21621577.entities.Originator;
import java.util.ArrayList;
import java.util.List;

public class Client {

  public static void main(String[] args) {

    Originator originator = new Originator();
    List<Memento> mementos = new ArrayList<>();

    saveSnapshot(originator, "Snapshot #0", mementos);
    saveSnapshot(originator, "Snapshot #1", mementos);
    saveSnapshot(originator, "Snapshot #2", mementos);

    originator.setState("Snapshot #4");

    for (int i = mementos.size(); i > 0; i--) {
      originator.restore(mementos.get(i - 1));
    }
  }

  private static void saveSnapshot(Originator originator, String checkPoint, List<Memento> mementos) {

    originator.setState(checkPoint);
    Memento memento = originator.getMemento();
    System.out.println("Saving this checkpoint.");
    mementos.add(memento);
  }
}
