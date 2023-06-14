package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Handler;

public class UnknownErrorHandler implements Handler {

  private Handler nextHandler;

  @Override
  public void handleMessage(Message message) {

    if (!(message.getText().contains("fax") || message.getText().contains("email"))) {
      System.out.println("An unknown error occurred. Consult experts immediately.");
    } else if (nextHandler != null) {
      nextHandler.handleMessage(message);
    }
  }

  @Override
  public void nextErrorHandler(Handler handler) {

    nextHandler = handler;
  }
}
