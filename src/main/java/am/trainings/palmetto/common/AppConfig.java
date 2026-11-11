package am.trainings.palmetto.common;

import am.trainings.palmetto.common.db.ConnectionFactory;
import am.trainings.palmetto.customer.repository.JdbcOrderRepository;
import am.trainings.palmetto.customer.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "am.trainings.palmetto")
public class AppConfig {

    @Bean
    public OrderRepository orderRepository (ConnectionFactory connectionFactory) {

        //
        return new JdbcOrderRepository(connectionFactory);
    }
}
