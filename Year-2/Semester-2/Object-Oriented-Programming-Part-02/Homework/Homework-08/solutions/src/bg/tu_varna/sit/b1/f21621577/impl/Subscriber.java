package bg.tu_varna.sit.b1.f21621577.impl;

import bg.tu_varna.sit.b1.f21621577.base.Observer;

public class Subscriber implements Observer {

  private final String subscriberName;
  private Channel subscribedChannel;

  public Subscriber(String subscriberName) {
    this.subscriberName = subscriberName;
  }

  @Override
  public void update() {
    System.out.println(subscriberName + ": New video uploaded on channel - " + subscribedChannel.getName());
  }

  public void subscribeToChannel(Channel channel) {

    this.subscribedChannel = channel;
    channel.addObserver(this);
  }

  public void unsubscribeFromChannel() {

    subscribedChannel.removeObserver(this);
    subscribedChannel = null;
  }
}