package bg.tu_varna.sit.b1.f21621577.impl;

import bg.tu_varna.sit.b1.f21621577.base.Observer;
import bg.tu_varna.sit.b1.f21621577.base.Subject;

import java.util.ArrayList;
import java.util.List;

public class Channel implements Subject {

  private final List<Observer> subscribers;
  private final String name;
  private boolean newVideoAvailable;

  public Channel(String name) {
    this.name = name;
    this.subscribers = new ArrayList<>();
    this.newVideoAvailable = false;
  }

  @Override
  public void addObserver(Observer observer) {

    if (observer == null) throw new NullPointerException("Null Observer");

    if (!subscribers.contains(observer)) {
      subscribers.add(observer);
    }
  }

  @Override
  public void removeObserver(Observer observer) {
    subscribers.remove(observer);
  }

  @Override
  public void notifyObservers() {

    List<Observer> observersLocal;
    if (!newVideoAvailable)
      return;
    observersLocal = new ArrayList<>(this.subscribers);
    this.newVideoAvailable = false;
    for (Observer obj : observersLocal) {
      obj.update();
    }
  }

  public void uploadNewVideo() {

    newVideoAvailable = true;
    notifyObservers();
  }

  public String getName() {
    return name;
  }
}