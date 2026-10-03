package am.trainings;


/**
 * add ability to order several types of pizzas
 */
public class Order {

    private static final int MAXIMUM_NUMBER_OF_INGREDIENTS = 7;
    public static final int NAME_MAX_LENGTH = 20;
    private static int orderCounter = 10_000;
    private final int orderNumber;
    private int customerNumber;
    private Pizza pizza;
    private int quantity;

    public Order(Customer customer, String[] ingredients,
                 String type,
                 String name,
                 int quantity) {
        this.orderNumber = orderCounter++;
        this.customerNumber = customer.getCustomerNumber();
        if (name.length() < 4 || name.length() > NAME_MAX_LENGTH || !isAllLatin(name)) {
            name = customer.getName() + "_" + orderNumber;
        }
        this.pizza = new Pizza(name, type, ingredients);
        this.quantity = quantity;
    }

    public Order(Customer customer,
                 String type,
                 String name,
                 int quantity) {

        this.orderNumber = orderCounter++;
        this.customerNumber = customer.getCustomerNumber();
        if (name.length() < 4 || name.length() > NAME_MAX_LENGTH || !isAllLatin(name)) {
            name = customer.getName() + "_" + orderNumber;
        }
        String[] ingredients = new String[7];
        this.pizza = new Pizza(name, type, ingredients);
        this.quantity = quantity;
    }


    public void addIngredient(String ingredient) {
        if (ingredient == null) {
            return;
        }
        String[] ingredients = pizza.getIngredients();

        int ingredientsCount = pizza.getIngredientsCount();
        if (ingredientsCount == MAXIMUM_NUMBER_OF_INGREDIENTS) {
            System.out.println("Pizza is full.");
        }

        for (int i = 0; i < ingredientsCount; i++) {
            String s = ingredients[i];
            if (ingredient.equals(s)) {
                System.out.println("Ingredient " + ingredient + "already added to pizza, please check your order");
                return;
            }
        }

        pizza.addIngredient(ingredient);

    }


    /**
     * 20 * 26 = 520  -> n^2
     */
    private boolean isAllLatin(String name) {
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!isLatin(c)) {
                return false;
            }
        }
        return true;
    }


    private boolean isLatin(char c) {

        return c >= 'a' && c <= 'z';
    }

    // [Order: Customer: Name: Quantity]
    public String getOrderDescription() {
        return "[" + orderNumber + ": " + customerNumber + ": " + pizza.getName() + ": " + quantity + "]";
    }

    public Pizza getPizza() {
        return pizza;
    }
}
