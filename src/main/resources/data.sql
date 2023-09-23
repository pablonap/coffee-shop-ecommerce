INSERT INTO carts (id, user_id, create_at) VALUES
    (1, 1, '2023-09-24 15:00:00'),
    (2, 2, '2023-09-24 15:30:00');

INSERT INTO products (id, name, price, category) VALUES
    (1, 'american coffee', 4, 'coffee'),
    (2, 'indian coffee', 4, 'coffee'),
    (3, 'small bag', 5, 'equipment'),
    (4, 'coffee machine', 50, 'accessories'),
    (5, 'spoon', 10, 'accessories'),
    (6, 'persian coffee', 4, 'coffee');

INSERT INTO cart_product (cart_id, product_id, quantity) VALUES
    (1, 1, 2),
    (1, 2, 1),
    (1, 3, 4),
    (1, 4, 2),
    (1, 5, 1),
    (2, 6, 1);