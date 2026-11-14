package am.trainings.palmetto.common.db;

import am.trainings.palmetto.common.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
@Component
@PropertySource(value = "classpath:db.properties")
public final class ConnectionFactory {

    private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;
    private boolean schemaInitialized;

    private ConnectionFactory() {
        // Drivers bundled in WEB-INF/lib are not auto-registered with DriverManager under Tomcat
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("H2 driver not found on classpath", e);
        }
    }

    public Connection getConnection() {
        try {
            initSchemaOnce();
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            log.error("Cannot open database connection to {}", url, e);
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
            log.error("Cannot load schema.sql", e);
            throw new DataAccessException("Cannot load schema.sql", e);
        }
        schemaInitialized = true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
