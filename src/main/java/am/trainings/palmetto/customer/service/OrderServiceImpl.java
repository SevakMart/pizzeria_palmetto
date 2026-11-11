package am.trainings.palmetto.customer.service;

import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.customer.exceptions.UserNotFoundException;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.repository.OrderRepository;
import am.trainings.palmetto.pizza.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class OrderServiceImpl implements OrderService {


    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        System.out.println("OrderServiceImpl created: init phase 2");
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

    @Autowired
    public void setCustomerRepository (CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    @Qualifier("orderRepository")
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
