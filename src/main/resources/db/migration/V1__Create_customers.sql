create table customers
(
    id           int8         not null,
    credit_limit int4         not null,
    email        varchar(255) not null,
    name         varchar(255) not null,
    profile      jsonb,
    primary key (id)
);

alter table customers
    add constraint uk_customer_email unique (email);

CREATE SEQUENCE customers_sequence;

