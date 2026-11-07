package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.common.DataAccessException;
import am.trainings.palmetto.common.db.ConnectionFactory;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.pizza.Pizza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// java se - standard edition
// java ee - enterprise edition
// java me - micro edition
// java database connectivity
public class JdbcOrderRepository implements OrderRepository {

    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (customer_id) VALUES (?)";
    private static final String SELECT_ALL_ORDERS_SQL = "SELECT * FROM orders";
    private static final String INSERT_ITEM_SQL =
            "INSERT INTO order_item (order_id, pizza_type, quantity) VALUES (?, ?, ?)";

    private final ConnectionFactory connectionFactory;

    public JdbcOrderRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * The order row and its items are saved in one transaction: all or nothing.
     */
    @Override
    public long save(Order order) {
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long orderId = insertOrder(connection, order);
                insertItems(connection, orderId, order.getPizzas());
                connection.commit();
                order.setOrderId(orderId);
                return orderId;
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot save order for customer " + order.getCustomerId(), e);
        }
    }

    @Override
    public List<Order> findAll() {

        try (
             Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_SQL);
        ){
            statement.execute();
            try (ResultSet rs = statement.getResultSet()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    long orderId = rs.getLong("id");
                    long customerId = rs.getLong("customer_id");
                    Order order = new Order();
                    order.setCustomerId(customerId);
                    order.setOrderId(orderId);

                    orders.add(order);
                }
                return orders;
            }

        }
        catch (SQLException e) {
            throw new DataAccessException("Cannot find all orders", e);
        }
    }


    private long insertOrder(Connection connection, Order order) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getCustomerId());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    private void insertItems(Connection connection, long orderId, Map<Pizza, Integer> pizzas) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ITEM_SQL)) {
            for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
                statement.setLong(1, orderId);
                statement.setString(2, entry.getKey().getType());
                statement.setInt(3, entry.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
}
