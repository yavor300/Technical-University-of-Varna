package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Handler;

public class FaxErrorHandler implements Handler {

  private Handler nextHandler;

  @Override
  public void handleMessage(Message message) {

    if (message.getText().contains("fax")) {
      if (message.getText().contains("email")) {
        System.out.println("FaxErrorHandler fixed the fax issue: " + message.getText());
        System.out.println("Now EmailErrorHandler needs to cross verify.");

        if (nextHandler != null) {
          nextHandler.handleMessage(message);
        }
      } else {
        System.out.println("FaxErrorHandler processed the issue: " + message.getText());
      }
    } else {
      if (nextHandler != null) {
        nextHandler.handleMessage(message);
      }
    }
  }

  @Override
  public void nextErrorHandler(Handler handler) {

    nextHandler = handler;
  }
}
