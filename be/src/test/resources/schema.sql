CREATE TABLE IF NOT EXISTS menu (
    id          bigint          NOT NULL auto_increment,
    category_id bigint          NOT NULL,
    name        varchar(20)     NOT NULL,
    price       bigint          NOT NULL,
    image       varchar(1000),
    create_at   datetime        NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS orders (
    id            bigint   NOT NULL auto_increment,
    payment_type  int      NOT NULL,
    payment_price bigint   NOT NULL,
    total_price   bigint,
    remain        bigint,
    order_at      datetime NOT NULL,
    order_number  bigint,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS order_detail (
    id          bigint   NOT NULL auto_increment,
    menu_id     bigint   NOT NULL,
    orders_id   bigint   NOT NULL,
    count       int      NOT NULL,
    create_at   datetime NOT NULL,
    size        int      NOT NULL,
    temperature int      NOT NULL,
    price       bigint   NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS category (
    id        bigint      NOT NULL auto_increment,
    name      varchar(20) NOT NULL,
    create_at datetime    NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS menu_rank (
    id      bigint   NOT NULL auto_increment,
    menu_id bigint   NOT NULL,
    sell_at date NOT NULL,
    PRIMARY KEY(id)
);
