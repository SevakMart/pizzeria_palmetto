package am.trainings.palmetto.customer.service;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.customer.exceptions.UserNotFoundException;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.repository.OrderRepository;
import am.trainings.palmetto.pizza.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService {


    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {

        log.info("Seeding demo customers and orders");
        Pizza pizza = new Pizza("margarita", "regular", new String[0]);

        registerIfAbsent("Sevak Martirosyan", "sevak");
        registerIfAbsent("Roland", "Roland");

        orderPizza("sevak", Map.of());
        orderPizza("Roland", Map.of(pizza, 1));

    }

    private void registerIfAbsent(String fullName, String username) {
        if (customerRepository.findCustomerByUsername(username).isEmpty()) {
            customerRepository.registerCustomer(new Customer(fullName, username));
            log.info("Registered customer {}", username);
        }
    }

    @Override
    public long orderPizza(String username, Map<Pizza, Integer> pizzas) {

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        Order order = new Order();
        order.setCustomerId(customer.getCustomerId());
        order.setPizzas(pizzas);
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findOrderById(long orderIdLong) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getOrderId().equals(orderIdLong)).findFirst();
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    @Qualifier("orderRepository")
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
