package bg.tu_varna.sit.b1.f21621577.implementation.order;

import bg.tu_varna.sit.b1.f21621577.base.entities.Product;
import bg.tu_varna.sit.b1.f21621577.base.state.OrderState;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<Product> products;
    private double totalPrice;
    private OrderState state;

    public Order() {
        products = new ArrayList<>();
        state = new OrderReceivedState();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void process() {
        state.process(this);
    }

    public double getTotalPrice() {
        OrderPriceVisitor visitor = new OrderPriceVisitor();
        for (Product product : products) {
            product.accept(visitor);
        }
        totalPrice = visitor.getTotalPrice();
        return totalPrice;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}