package bg.tu_varna.sit.b1.f21621577.base;

import bg.tu_varna.sit.b1.f21621577.entities.Message;

public interface Handler {

  void handleMessage(Message message);

  void nextErrorHandler(Handler handler);
}
