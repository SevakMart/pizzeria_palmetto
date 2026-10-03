package am.trainings;

public class Customer {

    private int customerNumber;
    private String name;

    public Customer(int customerNumber, String name) {
        this.customerNumber = customerNumber;
        this.name = name;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public String getName() {
        return name;
    }
}
