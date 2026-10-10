package am.trainings.palmetto.customer.service;

import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.customer.exceptions.UserNotFoundException;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.repository.OrderRepository;
import am.trainings.palmetto.pizza.Pizza;

import java.util.Map;

public class OrderServiceImpl implements OrderService {


    private final CustomerRepository customerRepository =
            new ApplicationContextHolder<CustomerRepository>().getObject(CustomerRepository.class);


    private final OrderRepository orderRepository =
            new ApplicationContextHolder<OrderRepository>().getObject(OrderRepository.class);


    public long orderPizza(String username, Map<Pizza, Integer> pizzas) {

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        Order order = new Order(customer.getCustomerId(), pizzas);
        long savedId = orderRepository.save(order);
        return savedId;
    }
}
