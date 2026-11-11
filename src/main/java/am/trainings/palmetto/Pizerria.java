package am.trainings.palmetto;

import am.trainings.palmetto.common.AppConfig;
import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.service.OrderService;
import am.trainings.palmetto.customer.service.OrderServiceImpl;
import am.trainings.palmetto.pizza.Pizza;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * This is the main class of the Pizzeria application.
 * It creates a customer and an order, adds ingredients to the pizza,
 * and prints the final pizza details.
 */
public class Pizerria {

    public static void main(String[] args) {
//        ApplicationContextHolder applicationContextHolder = new ApplicationContextHolder();
//        registerCustomer(applicationContextHolder);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        OrderService orderService = applicationContext.getBean(OrderService.class);


        Pizza regular = Pizza.REGULAR;
        orderService.orderPizza("sevakmart", Map.of(regular, 3));


        orderService.findAll().forEach(order -> {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Pizzas: " + order.getPizzas());
        });
    }

    private static void registerCustomer(ApplicationContextHolder applicationContextHolder) {
        CustomerRepository customerRepository = applicationContextHolder.getObject(CustomerRepository.class);
        Customer customer = new Customer("Sevak Martirosyan", "sevakmart");
        String userName = customerRepository.registerCustomer(customer);
        System.out.println("Customer registered as " + userName);
    }

}
