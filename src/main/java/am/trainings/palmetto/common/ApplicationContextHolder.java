package am.trainings.palmetto.common;

import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.repository.CustomerRepositoryFactory;
import am.trainings.palmetto.customer.repository.OrderRepository;
import am.trainings.palmetto.customer.repository.OrderRepositoryFactory;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextHolder<T> {

    private static final RepositoryTypes REPOSITORY_TYPE = RepositoryTypes.IN_MEMORY;

    private static final Map<Class, Object> CONTEXT = new HashMap<>();

    static {
        CONTEXT.put(CustomerRepository.class, new CustomerRepositoryFactory());
        CONTEXT.put(OrderRepository.class, new OrderRepositoryFactory());
    }


    public T getObject(Class<T> clazz) {

        Object o = CONTEXT.get(clazz);
        if (o instanceof AbstractRepositoryFactory) {
            return  (T)((AbstractRepositoryFactory) o).getRepositoryByType(REPOSITORY_TYPE);
        }

        return null;
    }
}
