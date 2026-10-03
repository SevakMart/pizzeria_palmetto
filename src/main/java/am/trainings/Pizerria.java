package am.trainings;

import org.json.JSONObject;

/**
 * This is the main class of the Pizzeria application.
 * It creates a customer and an order, adds ingredients to the pizza,
 * and prints the final pizza details.
 */
public class Pizerria {

    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "sevak");
        jsonObject.put("email", "sevakmart@gmail.com");

        Customer customer = new Customer(1111, "Vardan");
        Order order = new Order(customer, "regular", "margarita", 2);


        // think how to print prices as well or calculate total price of the order
        order.addIngredient("Tomato paste");
        order.addIngredient("Garlic");
        order.addIngredient("Peper");
        order.addIngredient("Bacon");

        PizzaType[] values = PizzaType.values();
        for (PizzaType value : values) {
            value.ordinal();
            value.name();
            System.out.println(value);
        }
    }
}
