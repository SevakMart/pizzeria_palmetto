package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.common.DataAccessException;
import am.trainings.palmetto.common.db.ConnectionFactory;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.exceptions.DuplicateUsernameException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// sql injections
@Component
public class JdbcCustomerRepository implements CustomerRepository {

    // prepare statement is parametrized
    private static final String INSERT_SQL = "INSERT INTO customer (full_name, username) VALUES (?, ?)";

    private static final String SELECT_BY_USERNAME_SQL =
            "SELECT id, full_name, username FROM customer WHERE username = ?";
    private static final String UNIQUE_VIOLATION = "23505";

    private final ConnectionFactory connectionFactory;

    public JdbcCustomerRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public String registerCustomer(Customer customer) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getUsername());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    customer.setCustomerId(keys.getLong(1));
                }
            }
            return customer.getUsername();
        } catch (SQLException e) {
            if (UNIQUE_VIOLATION.equals(e.getSQLState())) {
                throw new DuplicateUsernameException("Username: " + customer.getUsername() + " already exists");
            }
            throw new DataAccessException("Cannot register customer " + customer.getUsername(), e);
        }
    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME_SQL)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {

                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot find customer " + username, e);
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer customer = new Customer(rs.getString("full_name"), rs.getString("username"));
        customer.setCustomerId(rs.getLong("id"));
        return customer;
    }
}
