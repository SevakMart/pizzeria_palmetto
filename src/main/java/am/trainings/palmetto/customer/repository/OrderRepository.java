package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.common.BaseRepository;
import am.trainings.palmetto.customer.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository {

    long save(Order order);

    List<Order> findAll();
}
