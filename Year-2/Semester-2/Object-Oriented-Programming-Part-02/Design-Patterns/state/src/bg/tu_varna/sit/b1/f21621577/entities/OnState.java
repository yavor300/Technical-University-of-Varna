package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.PossibleState;

public class OnState implements PossibleState {

  public OnState() {
    System.out.println("The TV is On now.");
  }

  @Override
  public void pressOnButton(Television television) {

    System.out.print("The TV was already on.");
    System.out.println(" Ignoring repeated on button press operation.");
  }

  @Override
  public void pressOffButton(Television television) {

    System.out.println("The TV was on. So,switching off the TV.");
    television.setCurrentState(new OffState());
  }

  @Override
  public void pressMuteButton(Television television) {

    System.out.println("The TV was on. So,moving to the silent mode.");
    television.setCurrentState(new MuteState());
  }

  @Override
  public String toString(){
    return " Switched on.";
  }
}
