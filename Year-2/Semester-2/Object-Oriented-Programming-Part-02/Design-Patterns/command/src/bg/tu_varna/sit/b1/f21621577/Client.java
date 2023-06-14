package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.entities.Game;
import bg.tu_varna.sit.b1.f21621577.entities.GameStartCommand;
import bg.tu_varna.sit.b1.f21621577.entities.GameStopCommand;
import bg.tu_varna.sit.b1.f21621577.entities.RemoteControlInvoker;

public class Client {

  public static void main(String[] args) {

      Game game = new Game("Golf");
      GameStartCommand gameStartCommand = new GameStartCommand(game);
      GameStopCommand gameStopCommand = new GameStopCommand(game);
      RemoteControlInvoker remoteControlInvoker = new RemoteControlInvoker();
      remoteControlInvoker.setCurrent(gameStartCommand);
      remoteControlInvoker.executeSelectedCommand();
      remoteControlInvoker.undoCommand();
  }
}
