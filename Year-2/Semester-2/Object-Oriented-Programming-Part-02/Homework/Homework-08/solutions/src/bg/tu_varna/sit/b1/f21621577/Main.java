package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.impl.Channel;
import bg.tu_varna.sit.b1.f21621577.impl.Subscriber;

public class Main {

  public static void main(String[] args) {

    Channel channel = new Channel("Example Channel");

    Subscriber subscriber1 = new Subscriber("Subscriber 1");

    subscriber1.subscribeToChannel(channel);

    channel.uploadNewVideo();
  }
}