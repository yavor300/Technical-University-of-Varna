package bg.tu_varna.sit.b1.f21621577.base;

public interface Subject {

  void addObserver(Observer observer);

  void removeObserver(Observer observer);

  void notifyObservers();
}