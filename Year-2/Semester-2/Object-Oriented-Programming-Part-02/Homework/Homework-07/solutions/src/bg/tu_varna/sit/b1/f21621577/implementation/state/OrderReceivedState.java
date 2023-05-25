package bg.tu_varna.sit.b1.f21621577.implementation.state;

import bg.tu_varna.sit.b1.f21621577.base.state.OrderState;
import bg.tu_varna.sit.b1.f21621577.implementation.order.Order;

public class OrderReceivedState implements OrderState {

  @Override
  public void process(Order order) {
    order.setState(this);
  }
}
