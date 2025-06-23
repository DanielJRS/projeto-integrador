CREATE TABLE tb_usuario (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL
);

CREATE TABLE tb_empresa (
    id BIGSERIAL PRIMARY KEY,
    razao_social VARCHAR(255),
    nome_fantasia VARCHAR(255),
    cnpj VARCHAR(50),
    inscricao_estadual VARCHAR(50),
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(20),
    telefone VARCHAR(50),
    email VARCHAR(255),
    senha VARCHAR(255),
    data_fundacao DATE,
    usuario_id BIGINT UNIQUE,
    CONSTRAINT fk_empresa_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id)
);

CREATE TABLE tb_transportadora (
    id BIGSERIAL PRIMARY KEY,
    razao_social VARCHAR(255),
    nome_fantasia VARCHAR(255),
    cnpj VARCHAR(50),
    inscricao_estadual VARCHAR(50),
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(20),
    telefone VARCHAR(50),
    email VARCHAR(255),
    senha VARCHAR(255),
    data_fundacao DATE,
    ativo BOOLEAN,
    sou_transportadora BOOLEAN,
    numero_registro_antt VARCHAR(100),
    tipo_frota VARCHAR(100),
    quantidade_veiculos INTEGER,
    possui_seguro_carga BOOLEAN,
    tipos_mercadorias VARCHAR(255),
    capacidade_carga VARCHAR(100),
    rastreamento_veiculos BOOLEAN,
    prazo_padrao_entrega INTEGER,
    data_vencimento_licenca DATE,
    categorias_licenca VARCHAR(255),
    usuario_id BIGINT UNIQUE,
    CONSTRAINT fk_transportadora_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id)
);

CREATE TABLE tb_motorista (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cpf VARCHAR(20),
    endereco VARCHAR(255),
    celular VARCHAR(50),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    pais VARCHAR(50),
    cnh VARCHAR(50),
    antt VARCHAR(50),
    foto BYTEA,
    tipo_foto VARCHAR(50),
    usuario_id BIGINT UNIQUE,
    transportadora_id BIGINT,
    CONSTRAINT fk_motorista_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id),
    CONSTRAINT fk_motorista_transportadora FOREIGN KEY (transportadora_id) REFERENCES tb_transportadora(id)
);

CREATE TABLE tb_frete (
    id BIGSERIAL PRIMARY KEY,
    data_criacao VARCHAR(50),
    valor VARCHAR(50),
    origem_cidade VARCHAR(100),
    origem_estado VARCHAR(50),
    destino_estado VARCHAR(50),
    destino_cidade VARCHAR(100),
    produto VARCHAR(255),
    peso_total DOUBLE PRECISION,
    valor_carga VARCHAR(50),
    status VARCHAR(50),
    nome_fantasia VARCHAR(255),
    cnpj VARCHAR(50),
    telefone VARCHAR(50),
    email VARCHAR(255),
    motorista_id BIGINT,
    transportadora_id BIGINT,
    empresa_id BIGINT,
    CONSTRAINT fk_frete_motorista FOREIGN KEY (motorista_id) REFERENCES tb_motorista(id),
    CONSTRAINT fk_frete_transportadora FOREIGN KEY (transportadora_id) REFERENCES tb_transportadora(id),
    CONSTRAINT fk_frete_empresa FOREIGN KEY (empresa_id) REFERENCES tb_empresa(id)
);

CREATE TABLE cargas (
    id BIGSERIAL PRIMARY KEY,
    origem_cidade VARCHAR(100),
    origem_estado VARCHAR(50),
    data_coleta DATE,
    destino_cidade VARCHAR(100),
    destino_estado VARCHAR(50),
    data_entrega DATE,
    produto VARCHAR(255),
    especie VARCHAR(100),
    veiculo VARCHAR(100),
    preco DOUBLE PRECISION,
    preco_frete DOUBLE PRECISION,
    tipo_carga VARCHAR(50),
    tipo_estado_carga VARCHAR(50),
    possui_lona BOOLEAN,
    peso_total DOUBLE PRECISION,
    limite_altura DOUBLE PRECISION,
    volume DOUBLE PRECISION,
    motorista_id BIGINT,
    frete_id BIGINT UNIQUE,
    empresa_id BIGINT,
    CONSTRAINT fk_carga_motorista FOREIGN KEY (motorista_id) REFERENCES tb_motorista(id),
    CONSTRAINT fk_carga_frete FOREIGN KEY (frete_id) REFERENCES tb_frete(id),
    CONSTRAINT fk_carga_empresa FOREIGN KEY (empresa_id) REFERENCES tb_empresa(id)
);

CREATE TABLE carga_veiculos_leves (
    carga_id BIGINT NOT NULL,
    veiculo_leve VARCHAR(100),
    CONSTRAINT fk_carga_veiculos_leves FOREIGN KEY (carga_id) REFERENCES cargas(id) ON DELETE CASCADE
);

CREATE TABLE carga_veiculos_medios (
    carga_id BIGINT NOT NULL,
    veiculo_medio VARCHAR(100),
    CONSTRAINT fk_carga_veiculos_medios FOREIGN KEY (carga_id) REFERENCES cargas(id) ON DELETE CASCADE
);

CREATE TABLE carga_veiculos_pesados (
    carga_id BIGINT NOT NULL,
    veiculo_pesado VARCHAR(100),
    CONSTRAINT fk_carga_veiculos_pesados FOREIGN KEY (carga_id) REFERENCES cargas(id) ON DELETE CASCADE
);

CREATE TABLE carga_fretes_fechados (
    carga_id BIGINT NOT NULL,
    frete_fechado VARCHAR(100),
    CONSTRAINT fk_carga_fretes_fechados FOREIGN KEY (carga_id) REFERENCES cargas(id) ON DELETE CASCADE
);

CREATE TABLE carga_fretes_abertos (
    carga_id BIGINT NOT NULL,
    frete_aberto VARCHAR(100),
    CONSTRAINT fk_carga_fretes_abertos FOREIGN KEY (carga_id) REFERENCES cargas(id) ON DELETE CASCADE
);

CREATE TABLE carga_fretes_especiais (
    carga_id BIGINT NOT NULL,
    frete_especial VARCHAR(100),
    CONSTRAINT fk_carga_fretes_especiais FOREIGN KEY (carga_id) REFERENCES cargas(id) ON DELETE CASCADE
);

CREATE TABLE tb_veiculo (
    id BIGSERIAL PRIMARY KEY,
    placa VARCHAR(20),
    modelo VARCHAR(100),
    marca VARCHAR(100),
    capacidade_carga DOUBLE PRECISION,
    renavam VARCHAR(50),
    chassi VARCHAR(50),
    motorista_id BIGINT,
    transportadora_id BIGINT,
    CONSTRAINT fk_veiculo_motorista FOREIGN KEY (motorista_id) REFERENCES tb_motorista(id),
    CONSTRAINT fk_veiculo_transportadora FOREIGN KEY (transportadora_id) REFERENCES tb_transportadora(id)
);

CREATE TABLE veiculo_tipos (
    veiculo_id BIGINT NOT NULL,
    tipo VARCHAR(100),
    CONSTRAINT fk_veiculo_tipos FOREIGN KEY (veiculo_id) REFERENCES tb_veiculo(id) ON DELETE CASCADE
);

CREATE TABLE veiculo_fretes_fechados (
    veiculo_id BIGINT NOT NULL,
    frete_fechado VARCHAR(100),
    CONSTRAINT fk_veiculo_fretes_fechados FOREIGN KEY (veiculo_id) REFERENCES tb_veiculo(id) ON DELETE CASCADE
);

CREATE TABLE veiculo_fretes_abertos (
    veiculo_id BIGINT NOT NULL,
    frete_aberto VARCHAR(100),
    CONSTRAINT fk_veiculo_fretes_abertos FOREIGN KEY (veiculo_id) REFERENCES tb_veiculo(id) ON DELETE CASCADE
);

CREATE TABLE veiculo_fretes_especiais (
    veiculo_id BIGINT NOT NULL,
    frete_especial VARCHAR(100),
    CONSTRAINT fk_veiculo_fretes_especiais FOREIGN KEY (veiculo_id) REFERENCES tb_veiculo(id) ON DELETE CASCADE
);