CREATE TABLE cobranca (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(150),
    valor_total NUMERIC(12, 2) NOT NULL,
    data_emissao DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    cliente_id BIGINT NOT NULL,

    CONSTRAINT fk_cobranca_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
);

CREATE TABLE parcela (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER NOT NULL,
    valor NUMERIC(12, 2) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    status VARCHAR(30) NOT NULL,
    cobranca_id BIGINT NOT NULL,

    CONSTRAINT fk_parcela_cobranca
        FOREIGN KEY (cobranca_id)
        REFERENCES cobranca(id)
);