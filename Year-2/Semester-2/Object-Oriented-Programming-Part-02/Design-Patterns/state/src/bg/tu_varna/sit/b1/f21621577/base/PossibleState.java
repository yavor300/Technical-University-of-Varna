package bg.tu_varna.sit.b1.f21621577.base;

import bg.tu_varna.sit.b1.f21621577.entities.Television;

public interface PossibleState {

  void pressOnButton(Television television);

  void pressOffButton(Television television);

  void pressMuteButton(Television television);
}
