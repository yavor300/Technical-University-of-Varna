package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Handler;

public class EmailErrorHandler implements Handler {

  private Handler nextHandler;

  @Override
  public void handleMessage(Message message) {

    if (message.getText().contains("email")) {
      if (message.getText().contains("fax")) {
        System.out.println("EmailErrorHandler fixed the email issue: " + message.getText());
        System.out.println("Now FaxErrorHandler needs to cross verify.");

        if (nextHandler != null) {
          nextHandler.handleMessage(message);
        }
      } else {
        System.out.println("EmailErrorHandler processed the issue: " + message.getText());
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
