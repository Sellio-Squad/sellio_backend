-- UUIDs:
-- USER_ID_OWNER:   'f895cdbe-73fc-4e44-b5db-02f396953f64'
-- USER_ID_CUSTOMER: 'c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0'
-- STORE_ID:        '57a212fc-e4ac-4f70-90cd-21f95dc600ba'
-- CAT_ID_ELECTRONICS: '44444444-c5c5-d6d6-e7e7-444444444444'
-- SUB_CAT_ID_LAPTOPS: '33333333-d4d4-e5e5-f6f6-333333333333'
-- PRODUCT_ID_GAMING: '11111111-a1a1-b2b2-c3c3-111111111111'
-- PRODUCT_ID_OFFICE: '22222222-a1a1-b2b2-c3c3-222222222222'
-- ITEM_ID_1 (Product Item): 'a1a1a1a1-aaaa-aaaa-aaaa-000000000001'
-- ORDER_ID_1 (Orders):     'b1b1b1b1-bbbb-bbbb-bbbb-000000000001'
-- CART_ID_1 (Cart):        'c1c1c1c1-cccc-cccc-cccc-000000000001'


INSERT INTO color (id, value)
VALUES (1, 'Red');
INSERT INTO color (id, value)
VALUES (2, 'Black');

INSERT INTO sizes (id, value)
VALUES (1, 'Small');
INSERT INTO sizes (id, value)
VALUES (2, 'Medium');

INSERT INTO users (id, first_name, last_name, email, password, phone_number, city, country, created_at, updated_at)
VALUES ('f895cdbe-73fc-4e44-b5db-02f396953f64',
        'Ahmed',
        'Sayed',
        'ahmed@sellio.com',
        'pass_hash',
        '01000000001',
        'Cairo',
        'Egypt',
        NOW(),
        NOW()),

       ('c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0',
        'Abdulaziz',
        'Anwer',
        'aziz@sellio.com',
        'pass_hash',
        '01000000002',
        'Samaraa',
        'Iraq',
        NOW(),
        NOW()
       ),

       ('e5e8e9ce-c0c0-c9c0-c8c0-c0c0c0e5c8e9',
        'Karrar',
        'Abbas',
        'karrar@sellio.com',
        'pass_hash',
        '01000000003',
        'Baghdad',
        'Iraq',
        NOW(),
        NOW()
       );

INSERT INTO store (id, owner_id, title, description, phone_number, city, government, country, created_at, updated_at)
VALUES ('57a212fc-e4ac-4f70-90cd-21f95dc600ba',
        'f895cdbe-73fc-4e44-b5db-02f396953f64',
        'Global Tech Store',
        'High-end electronics store for developers.',
        '01212121212',
        'Maadi',
        'Cairo',
        'Egypt',
        NOW(),
        NOW()
       );

INSERT INTO category (id, title, created_at, updated_at)
VALUES ('44444444-c5c5-d6d6-e7e7-444444444444',
        'Electronics',
        NOW(),
        NOW()
       );


INSERT INTO sub_category (id, parent_id, title, created_at, updated_at)
VALUES ('33333333-d4d4-e5e5-f6f6-333333333333',
        '44444444-c5c5-d6d6-e7e7-444444444444',
        'Laptops',
        NOW(),
        NOW());


INSERT INTO product (id, store_id, title, description, price, is_used, is_featured, created_at, updated_at)
VALUES ('11111111-a1a1-b2b2-c3c3-111111111111',
        '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
        'Razer Gaming Laptop',
        'High-end gaming machine for test.',
        1500.0,
        FALSE,
        TRUE,
        NOW(),
        NOW()
       ),

       ('22222222-a1a1-b2b2-c3c3-222222222222',
        '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
        'Budget Office PC',
        'Reliable computer for daily tasks.',
        900.0,
        FALSE,
        FALSE,
        NOW(),
        NOW()
       );

INSERT INTO product_subcategory (id, product_id, sub_category_id, created_at)
VALUES (gen_random_uuid(),
        '11111111-a1a1-b2b2-c3c3-111111111111',
        '33333333-d4d4-e5e5-f6f6-333333333333',
        NOW()
       );

INSERT INTO product_image (id, product_id, image_url, created_at)
VALUES (gen_random_uuid(),
        '11111111-a1a1-b2b2-c3c3-111111111111',
        'https://s3.aws/image_a1.jpg',
        NOW()
       );

INSERT INTO product_item (id, product_id, price, stock, created_at, updated_at)
VALUES ('a1a1a1a1-aaaa-aaaa-aaaa-000000000001',
        '11111111-a1a1-b2b2-c3c3-111111111111',
        1500.0,
        5,
        NOW(),
        NOW());


INSERT INTO store_rating (id, store_id, user_id, rating_value, created_at, updated_at)
VALUES (gen_random_uuid(),
        '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
        'c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0',
        5,
        NOW(),
        NOW()
       ),
       (gen_random_uuid(),
        '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
        'f895cdbe-73fc-4e44-b5db-02f396953f64',
        3,
        NOW(),
        NOW()
       );

INSERT INTO favorite_product (id, user_id, product_id, created_at)
VALUES (gen_random_uuid(),
        'c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0',
        '22222222-a1a1-b2b2-c3c3-222222222222',
        NOW()
       );

INSERT INTO favorite_store (id, user_id, store_id, created_at)
VALUES (gen_random_uuid(),
        'c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0',
        '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
        NOW()
       );

INSERT INTO discounts (id, store_id, product_id, category_id, sub_category_id, type, value, start_date, end_date)
VALUES (gen_random_uuid(),
        '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
        NULL,
        NULL,
        NULL,
        'PERCENTAGE',
        10.0,
        NOW(),
        NOW() + INTERVAL '30 day');

INSERT INTO cart (id, user_id, created_at, updated_at)
VALUES ('c1c1c1c1-cccc-cccc-cccc-000000000001',
        'c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0',
        NOW(),
        NOW()
       );

INSERT INTO cart_item (id, cart_id, product_item_id, quantity, created_at, updated_at)
VALUES (gen_random_uuid(),
        'c1c1c1c1-cccc-cccc-cccc-000000000001',
        'a1a1a1a1-aaaa-aaaa-aaaa-000000000001',
        1,
        NOW(),
        NOW()
       );

INSERT INTO orders (id, user_id, status, created_at, updated_at)
VALUES ('b1b1b1b1-bbbb-bbbb-bbbb-000000000001',
        'c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0',
        'IN_PROGRESS',
        NOW(),
        NOW());

INSERT INTO order_item (id, product_item_id, order_id, status, quantity, created_at, updated_at)
VALUES (gen_random_uuid(),
        'a1a1a1a1-aaaa-aaaa-aaaa-000000000001',
        'b1b1b1b1-bbbb-bbbb-bbbb-000000000001',
        'IN_PROGRESS',
        1,
        NOW(),
        NOW()
       );

INSERT INTO discounts (id, store_id, product_id, category_id, sub_category_id, type, value, start_date, end_date)
VALUES
    (gen_random_uuid(),
     '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
     '11111111-a1a1-b2b2-c3c3-111111111111',
     NULL, NULL,
     'FIXED',
     200.0,
     NOW(),
     NOW() + INTERVAL '15 day');

INSERT INTO discounts (id, store_id, product_id, category_id, sub_category_id, type, value, start_date, end_date)
VALUES
    (gen_random_uuid(),
     '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
     NULL,
     '44444444-c5c5-d6d6-e7e7-444444444444',
     NULL,
     'PERCENTAGE',
     5.0,
     NOW(),
     NOW() + INTERVAL '7 day');

INSERT INTO discounts (id, store_id, product_id, category_id, sub_category_id, type, value, start_date, end_date)
VALUES
    (gen_random_uuid(),
     '57a212fc-e4ac-4f70-90cd-21f95dc600ba',
     NULL, NULL,
     '33333333-d4d4-e5e5-f6f6-333333333333',
     'PERCENTAGE',
     15.0,
     NOW(),
     NOW() + INTERVAL '3 day');


INSERT INTO color (id, value)
VALUES (3, 'White'),
       (4, 'Green'),
       (5, 'Pink');

INSERT INTO sizes (id, value)
VALUES (4, 'Large'),
       (5, 'XL'),
       (6, '2XL');




INSERT INTO users (id, first_name, last_name, email, password, phone_number, city, country, created_at, updated_at)
VALUES ('00000000-0000-0000-0000-000000000001',
        'Sellio',
        'Admin',
        'admin@sellio.com',
        'admin_pass_hash',
        '01010101010',
        'Cairo',
        'Egypt',
        NOW(),
        NOW());

INSERT INTO store (id, owner_id, title, description, phone_number, city, government, country, created_at, updated_at)
VALUES ('00000000-0000-0000-0000-000000000002',
        '00000000-0000-0000-0000-000000000001',
        'Sellio Customize',
        'Official store for customizable products.',
        '01515151515',
        'Cairo',
        'Cairo',
        'Egypt',
        NOW(),
        NOW());

INSERT INTO category (id, title, created_at, updated_at)
VALUES ('a0a0a0a0-0001-0001-0001-000000000001',
        'Apparel',
        NOW(),
        NOW()),
       ('a0a0a0a0-0001-0001-0001-000000000002',
        'Drinkware',
        NOW(),
        NOW());

INSERT INTO sub_category (id, parent_id, title, created_at, updated_at)
VALUES ('b0b0b0b0-0001-0001-0001-000000000001',
        'a0a0a0a0-0001-0001-0001-000000000001',
        'T-Shirts',
        NOW(),
        NOW()),
       ('b0b0b0b0-0001-0001-0001-000000000002',
        'a0a0a0a0-0001-0001-0001-000000000002',
        'Mugs',
        NOW(),
        NOW());

INSERT INTO product (id, store_id, title, description, price, is_used, is_featured, created_at, updated_at)
VALUES
    ('90909090-0001-0001-0001-000000000001',
     '00000000-0000-0000-0000-000000000002',
     'Customizable T-Shirt',
     'Base t-shirt',
     250.0,
     FALSE,
     TRUE,
     NOW(),
     NOW()),
    ('90909090-0001-0001-0001-000000000002',
     '00000000-0000-0000-0000-000000000002',
     'Customizable Mug',
     'White ceramic mug',
     150.0,
     FALSE,
     TRUE,
     NOW(),
     NOW());

INSERT INTO product_subcategory (id, product_id, sub_category_id, created_at)
VALUES (gen_random_uuid(),
        '90909090-0001-0001-0001-000000000001',
        'b0b0b0b0-0001-0001-0001-000000000001',
        NOW()),
       (gen_random_uuid(),
        '90909090-0001-0001-0001-000000000002',
        'b0b0b0b0-0001-0001-0001-000000000002',
        NOW());

INSERT INTO product_item (id, product_id, price, stock, color_id, size_id, variation_image_url, created_at, updated_at)
VALUES
    (gen_random_uuid(),
     '90909090-0001-0001-0001-000000000001',
     250.0,
     100,
     1,
     2,
     'https://s3.aws/tshirt-white.jpg',
     NOW(),
     NOW()),

    (gen_random_uuid(),
     '90909090-0001-0001-0001-000000000001',
     250.0, 100,
     'c010c010-0001-0001-0001-000000000003',
     '512e0000-0001-0001-0001-000000000003',
     'https://s3.aws/tshirt-white.jpg',
     NOW(),
     NOW()),

    (gen_random_uuid(),
     '90909090-0001-0001-0001-000000000001',
     250.0,
     100,
     'c010c010-0001-0001-0001-000000000002',
     '512e0000-0001-0001-0001-000000000002',
     'https://s3.aws/tshirt-black.jpg',
     NOW(),
     NOW()),

    (gen_random_uuid(),
     '90909090-0001-0001-0001-000000000001',
     250.0,
     100,
     'c010c010-0001-0001-0001-000000000002',
     '512e0000-0001-0001-0001-000000000003',
     'https://s3.aws/tshirt-black.jpg',
     NOW(),
     NOW()),

    ('a1a1a1a1-aaaa-aaaa-aaaa-000000000002',
     '90909090-0001-0001-0001-000000000002',
     150.0,
     100,
     'c010c010-0001-0001-0001-000000000003',
     NULL,
     'https://s3.aws/mug-white.jpg',
     NOW(),
     NOW());