package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Command;

public class GameStopCommand implements Command {

  private final Game game;

  public GameStopCommand(Game game) {
    this.game = game;
  }

  @Override
  public void executeCommand() {

    System.out.println("Finishing the game.");
    game.stop();
  }

  @Override
  public void undoPreviousCommand() {

    System.out.println("Undoing the stop command.");
    game.start();
    game.displayScore();
  }
}
