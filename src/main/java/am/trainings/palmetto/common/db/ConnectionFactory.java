package am.trainings.palmetto.common.db;

import am.trainings.palmetto.common.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Connection management: reads db.properties, opens a new Connection per call
 * and creates the schema once. Callers must close connections (try-with-resources).
 */
public final class ConnectionFactory {

    private static final ConnectionFactory INSTANCE = new ConnectionFactory();

    private final String url;
    private final String user;
    private final String password;
    private boolean schemaInitialized;

    private ConnectionFactory() {
        Properties props = new Properties();
        try (InputStream in = ConnectionFactory.class.getResourceAsStream("/db.properties")) {
            props.load(in);
        } catch (IOException | NullPointerException e) {
            throw new DataAccessException("Cannot load db.properties", e);
        }
        url = props.getProperty("db.url");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password", "");
    }

    public static ConnectionFactory getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            initSchemaOnce();
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataAccessException("Cannot open database connection", e);
        }
    }

    private synchronized void initSchemaOnce() throws SQLException {
        if (schemaInitialized) {
            return;
        }
        try (InputStream in = ConnectionFactory.class.getResourceAsStream("/schema.sql");
             Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            String script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            for (String sql : script.split(";")) {
                if (!sql.isBlank()) {
                    statement.execute(sql);
                }
            }
        } catch (IOException | NullPointerException e) {
            throw new DataAccessException("Cannot load schema.sql", e);
        }
        schemaInitialized = true;
    }
}
