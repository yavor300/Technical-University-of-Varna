package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.PossibleState;

public class OffState implements PossibleState {

  @Override
  public void pressOnButton(Television television) {
    System.out.println("The TV is Off now.");
  }

  @Override
  public void pressOffButton(Television television) {
    System.out.println("The TV was already in Off state.");
    System.out.println("So, ignoring this operation.");
  }

  @Override
  public void pressMuteButton(Television tvContext) {

    System.out.print("The TV was already off.");
    System.out.println(" So, ignoring this operation.");
  }

  @Override
  public String toString() {
    return "Switched off.";
  }
}
