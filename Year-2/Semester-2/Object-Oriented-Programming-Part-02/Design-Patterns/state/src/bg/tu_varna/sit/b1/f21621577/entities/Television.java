package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.PossibleState;

public class Television {

  private PossibleState currentState;

  public PossibleState getCurrentState() {
    return currentState;
  }

  public void setCurrentState(PossibleState currentState) {
    this.currentState = currentState;
  }

  public Television() {
    this.currentState = new OnState();
  }

  public void executeOffButton() {
    System.out.println("You pressed the Off button.");
    currentState.pressOffButton(this);
  }

  public void executeOnButton() {
    System.out.println("You pressed the On button.");
    currentState.pressOnButton(this);
  }

  public void executeMuteButton() {
    System.out.println("You pressed the Mute button.");
    currentState.pressMuteButton(this);
  }
}
