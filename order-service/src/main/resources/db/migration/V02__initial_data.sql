insert into customers
values(nextval('customers_customer_id_seq'), 'Bob Ross', '240 Wood Duck Drive');

insert into customers
values(nextval('customers_customer_id_seq'), 'Spongebob Squarepants', '92 St. Greenland');

insert into customers
values(nextval('customers_customer_id_seq'), 'BK the Cat', 'St. Under The Blanket');

insert into customers
values(nextval('customers_customer_id_seq'), 'Ola Nordmann', 'Ringgata');

insert into customers
values(nextval('customers_customer_id_seq'), 'Jessi', 'Fyrstikktorget');

insert into customers
values(nextval('customers_customer_id_seq'), 'Don Figaro', 'Figgy Iggy 123');


insert into orders
values(nextval('orders_order_id_seq'), now(), 'Board game', true, false, 1);

insert into orders
values(nextval('orders_order_id_seq'), now(), 'Perfume', true, false, 1);

insert into orders
values(nextval('orders_order_id_seq'), now(), 'Cat food', true, false, 4);

insert into orders
values(nextval('orders_order_id_seq'), now(), 'Board game', true, false, 3);

insert into orders
values(nextval('orders_order_id_seq'), now(), 'Perfume', false, false, 2);

insert into orders
values(nextval('orders_order_id_seq'), now(), 'Cat food', false, false, 1);