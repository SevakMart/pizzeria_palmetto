package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.customer.Customer;

import java.util.Optional;

public interface CustomerRepository {

    String registerCustomer (Customer customer);

    Optional<Customer> findCustomerByUsername(String username);
}
