package am.trainings.palmetto;

import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.service.OrderService;
import am.trainings.palmetto.customer.service.OrderServiceImpl;
import am.trainings.palmetto.pizza.Pizza;

import java.util.Map;

/**
 * This is the main class of the Pizzeria application.
 * It creates a customer and an order, adds ingredients to the pizza,
 * and prints the final pizza details.
 */
public class Pizerria {

    public static void main(String[] args) {
        ApplicationContextHolder applicationContextHolder = new ApplicationContextHolder();
        registerCustomer(applicationContextHolder);

        OrderService orderService = new OrderServiceImpl(applicationContextHolder);

        Pizza regular = Pizza.REGULAR;
        orderService.orderPizza("sevakmart", Map.of(regular, 3));
    }

    private static void registerCustomer(ApplicationContextHolder applicationContextHolder) {
        CustomerRepository customerRepository = applicationContextHolder.getObject(CustomerRepository.class);
        Customer customer = new Customer("Sevak Martirosyan", "sevakmart");
        String userName = customerRepository.registerCustomer(customer);
        System.out.println("Customer registered as " + userName);
    }

}
