package am.trainings.palmetto.customer.service;

import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.pizza.Pizza;

import java.util.List;
import java.util.Map;

public interface OrderService {
    long orderPizza(String username, Map<Pizza, Integer> pizzas);

    List<Order> findAll();
}
