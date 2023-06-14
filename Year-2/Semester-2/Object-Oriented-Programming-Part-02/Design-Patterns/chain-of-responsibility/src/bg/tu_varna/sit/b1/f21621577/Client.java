package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Handler;
import bg.tu_varna.sit.b1.f21621577.entities.EmailErrorHandler;
import bg.tu_varna.sit.b1.f21621577.entities.FaxErrorHandler;
import bg.tu_varna.sit.b1.f21621577.entities.Message;
import bg.tu_varna.sit.b1.f21621577.entities.UnknownErrorHandler;

public class Client {

  public static void main(String[] args) {

      Handler faxHandler = new FaxErrorHandler();
      Handler emailHandler = new EmailErrorHandler();
      Handler unknownHandler = new UnknownErrorHandler();

      Handler rootHandler;
      rootHandler = faxHandler;
      rootHandler.nextErrorHandler(emailHandler);
      emailHandler.nextErrorHandler(unknownHandler);
      unknownHandler.nextErrorHandler(null);

      Message message1 = new Message("The fax and email are slow.");
      rootHandler.handleMessage(message1);
  }
}
