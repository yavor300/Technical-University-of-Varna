package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Command;

public class GameStartCommand implements Command {

  private final Game game;

  public GameStartCommand(Game game) {
    this.game = game;
  }

  @Override
  public void executeCommand() {

    System.out.println("The game is about to start.");
    game.start();
    game.displayScore();
  }

  @Override
  public void undoPreviousCommand() {

    System.out.println("Undoing the start command.");
    game.stop();
  }
}
