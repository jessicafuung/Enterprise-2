create table customers
(
    customer_id bigserial primary key,
    customer_name varchar (50),
    customer_address varchar (50)
);

create table orders
(
    order_id bigserial primary key,
    order_date timestamp,
    order_description varchar(50),
    order_payment boolean not null,
    order_dispatched boolean not null,
    customer_id bigint references customers(customer_id)
);
