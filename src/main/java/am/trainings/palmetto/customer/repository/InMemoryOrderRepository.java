package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.customer.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


// dao - data access object
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();
    private static InMemoryOrderRepository INSTANCE = null;

    private InMemoryOrderRepository() {
    }

    public static InMemoryOrderRepository getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new InMemoryOrderRepository();
        }

        return INSTANCE;
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
