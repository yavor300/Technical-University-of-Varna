package bg.tu_varna.sit.b1.f21621577.entities;

public class Originator {

  private String state;

  public void setState(String state) {

    this.state = state;
    System.out.println("The current state is " + state);
  }

  public void restore(Memento memento) {

    this.state = memento.getState();
    System.out.println("Restored to state: " + state);
  }

  public Memento getMemento() {

    return new Memento(state);
  }
}
