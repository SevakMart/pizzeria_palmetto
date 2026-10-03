package am.trainings;

public class InplaceOrder extends Order {
    private String tableNumber;

    public InplaceOrder(Customer customer, String[] ingredients, String type, String name, int quantity, String tableNumber) {
        super(customer, ingredients, type, name, quantity);
        this.tableNumber = tableNumber;
    }

    public InplaceOrder(Customer customer, String type, String name, int quantity, String tableNumber) {
        super(customer, type, name, quantity);
        this.tableNumber = tableNumber;
    }

    public String getTableNumber() {
        return tableNumber;
    }
}
