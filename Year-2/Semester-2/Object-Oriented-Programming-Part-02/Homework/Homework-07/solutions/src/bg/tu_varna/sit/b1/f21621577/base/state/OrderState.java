package bg.tu_varna.sit.b1.f21621577.base.state;

import bg.tu_varna.sit.b1.f21621577.implementation.order.Order;

public interface OrderState {

    void process(Order order);
}