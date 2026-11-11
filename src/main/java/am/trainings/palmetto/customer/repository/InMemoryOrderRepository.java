package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.customer.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();
    public InMemoryOrderRepository() {
        System.out.println("InMemoryOrderRepository created");
    }



    private Long initialOrderId = 0L;


    public long save(Order order) {
        Long orderId = generateOrderNumber();
        order.setOrderId(orderId);
        orders.put(orderId, order);
        return orderId;
    }

    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }


    private Long generateOrderNumber() {
        return initialOrderId++;
    }

}
