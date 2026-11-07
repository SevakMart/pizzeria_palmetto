CREATE TABLE IF NOT EXISTS customer (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    username  VARCHAR(100) NOT NULL,
    CONSTRAINT uq_customer_username UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS orders (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE INDEX IF NOT EXISTS idx_orders_customer_id ON orders (customer_id);

-- Order.pizzas (Map<Pizza, Integer>) normalized into its own table (many-to-many)
CREATE TABLE IF NOT EXISTS order_item (
    order_id   BIGINT       NOT NULL,
    pizza_type VARCHAR(50)  NOT NULL,
    quantity   INT          NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (order_id, pizza_type),
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders (id)
);
