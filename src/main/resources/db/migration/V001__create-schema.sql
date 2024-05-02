CREATE TABLE tb_user (
    id VARCHAR(36) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    document VARCHAR(14) NOT NULL UNIQUE,
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NOT NULL,
    bairro VARCHAR(255) NOT NULL,
    localidade VARCHAR(255) NOT NULL,
    uf VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    date_of_birth DATE,
    wallet_id VARCHAR(36),
    card_id bigint,
    gender VARCHAR(10) NOT NULL,
    marital_status VARCHAR(50) NOT NULL,
    score bigint,

    primary key (id)
);

CREATE TABLE tb_wallet (
    id VARCHAR(36) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    balance INTEGER NOT NULL,
    user_id VARCHAR(36),

    primary key (id)
);

CREATE TABLE tb_card (
    id bigint NOT NULL auto_increment,
    card_number VARCHAR(20),
    user_id VARCHAR(36),
    expiration_date DATE NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    card_type VARCHAR(50) NOT NULL,
    card_limit INTEGER NOT NULL,
    card_flag VARCHAR(50),
    amount_spent INTEGER,

    primary key (id)
);

CREATE TABLE tb_transaction (
    id VARCHAR(36) NOT NULL,
    sender_id VARCHAR(36) NOT NULL,
    receiver_id VARCHAR(36) NOT NULL,
    amount INTEGER NOT NULL,
    transaction_date TIMESTAMP NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE tb_transaction ADD CONSTRAINT fk_transaction_sender
    FOREIGN KEY (sender_id) REFERENCES tb_wallet(id);

ALTER TABLE tb_transaction ADD CONSTRAINT fk_transaction_receiver
    FOREIGN KEY (receiver_id) REFERENCES tb_wallet(id);

ALTER TABLE tb_wallet ADD CONSTRAINT fk_wallet_user
FOREIGN KEY (user_id) REFERENCES tb_user(id);

ALTER TABLE tb_card ADD CONSTRAINT fk_card_user
 FOREIGN KEY (user_id) REFERENCES tb_user(id);
