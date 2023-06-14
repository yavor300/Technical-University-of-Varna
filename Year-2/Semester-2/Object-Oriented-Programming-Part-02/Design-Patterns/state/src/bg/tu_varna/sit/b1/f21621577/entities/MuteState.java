package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.PossibleState;

public class MuteState implements PossibleState {

  public MuteState() {
    System.out.println("The TV is in Mute mode now.\n");
  }

  @Override
  public void pressOnButton(Television television) {

    System.out.print("The TV was in mute mode.");
    System.out.println(" So, moving to the normal state.");
    television.setCurrentState(new OnState());
  }

  @Override
  public void pressOffButton(Television television) {

    System.out.print(" The TV was in mute mode.");
    System.out.println(" So, switching off the TV.");
    television.setCurrentState(new OffState());
  }

  @Override
  public void pressMuteButton(Television television) {

    System.out.print("The TV was already in Mute mode.");
    System.out.println("So, ignoring this operation.");
  }

  @Override
  public String toString(){
    return "Mute mode.";
  }
}
