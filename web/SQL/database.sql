-- -----------------------------------------------------
-- Database
-- -----------------------------------------------------

DROP DATABASE IF EXISTS PW2_TrabalhoFinalDB;
CREATE DATABASE PW2_TrabalhoFinalDB;
USE PW2_TrabalhoFinalDB;

-- -----------------------------------------------------
-- Database User
-- -----------------------------------------------------

CREATE USER 'anderson'@'localhost' IDENTIFIED BY 'senha';
GRANT ALL ON PW2_TrabalhoFinalDB.* TO 'anderson'@'localhost' WITH GRANT OPTION;

-- -----------------------------------------------------
-- Usuário
-- -----------------------------------------------------

CREATE TABLE Usuario (
    idUsuario INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(32) NOT NULL,
    email VARCHAR(32) NOT NULL,
    senha BINARY(16) NOT NULL,
    hash VARCHAR(32) NOT NULL,
    foto MEDIUMBLOB
);

DELIMITER $ CREATE PROCEDURE setUsuario (
    IN p_nome VARCHAR(32),
    IN p_email VARCHAR(32),
    IN p_senha VARCHAR(32),
    IN p_foto MEDIUMBLOB )
BEGIN
    IF (SELECT idUsuario FROM Usuario WHERE email = p_email) IS NULL THEN
        INSERT INTO Usuario VALUES (
            NULL,
            p_nome,
            p_email,
            UNHEX(MD5(p_senha)),
            MD5(CONCAT(p_email, '_', p_senha, '!')),
            p_foto );
    ELSE
        SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 
            "Este e-mail já está cadastrado no sistema!";
    END IF;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE isUsuario (
    IN p_email VARCHAR(32),
    IN p_senha VARCHAR(32) )
BEGIN
    IF ((SELECT senha FROM Usuario WHERE email = p_email) = 
            UNHEX(MD5(p_senha))) THEN
        SELECT TRUE AS 'Output', 
            idUsuario AS 'ID',
            nome AS 'Nome',
            hash AS 'Hash'
        FROM Usuario WHERE email = p_email;
    ELSE
        SELECT FALSE AS 'Output';
    END IF;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE isUsuarioHash (
    IN p_idUsuario INT,
    IN p_hash VARCHAR(32) )
BEGIN
    IF (SELECT hash FROM Usuario WHERE idUsuario = p_idUsuario) = p_hash THEN
        SELECT TRUE AS 'Output';
    ELSE
        SELECT FALSE AS 'Output';
    END IF;
END $ DELIMITER;

-- -----------------------------------------------------
-- Automovel
-- -----------------------------------------------------

CREATE TABLE Automovel (
    idAutomovel INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    nome VARCHAR(32) NOT NULL,
    marca VARCHAR(32) NOT NULL,
    anoModelo INT NOT NULL,
    dataAquisicao DATE NOT NULL,
    placa VARCHAR(8) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setAutomovel (
    IN p_idUsuario INT,
    IN p_nome VARCHAR(32),
    IN p_marca VARCHAR(32),
    IN p_anoModelo INT,
    IN p_dataAquisicao DATE,
    IN p_placa VARCHAR(8) )
BEGIN
    IF (SELECT idAutomovel FROM Automovel WHERE placa = p_placa AND 
idUsuario = p_idUsuario) IS NULL THEN
        INSERT INTO Automovel VALUES (
            NULL,
            p_idUsuario,
            p_nome,
            p_marca,
            p_anoModelo,
            p_dataAquisicao,
            p_placa );
    ELSE
        SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 
            "Esta placa já foi cadastrada no sistema por este usuário!";
    END IF;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updateAutomovel (
    IN p_idAutomovel INT,
    IN p_nome VARCHAR(32),
    IN p_marca VARCHAR(32),
    IN p_anoModelo INT,
    IN p_dataAquisicao DATE,
    IN p_placa VARCHAR(8) )
BEGIN
    UPDATE Automovel SET 
        nome = p_nome,
        marca = p_marca,
        anoModelo = p_anoModelo,
        dataAquisicao = p_dataAquisicao,
        placa = p_placa
    WHERE idAutomovel = p_idAutomovel;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deleteAutomovel (
    IN p_idAutomovel INT )
BEGIN
    DELETE FROM Automovel WHERE idAutomovel = p_idAutomovel ;
END $ DELIMITER;

DELIMITER $ CREATE VIEW getAutomoveis AS
    SELECT
        idUsuario AS 'ID do Usuário',
        idAutomovel AS 'ID do Automóvel',
        nome AS 'Nome do Automóvel',
        marca AS 'Marca do Automóvel',
        anoModelo AS 'Ano do Modelo do Automóvel',
        dataAquisicao AS 'Data de Aquisição do Automóvel',
        placa AS 'Placa do Automóvel'
    FROM Automovel
    ORDER BY idUsuario, nome;
$ DELIMITER;

-- -----------------------------------------------------
-- Combustivel
-- -----------------------------------------------------

CREATE TABLE Combustivel (
    idCombustivel INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    tipo VARCHAR(32) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setCombustivel (
    IN p_idUsuario INT,
    IN p_tipo VARCHAR(32) )
BEGIN
    INSERT INTO Combustivel VALUES (
        NULL,
        p_idUsuario,
        p_tipo );
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updateCombustivel (
    IN p_idCombustivel INT,
    IN p_tipo VARCHAR(32) )
BEGIN
    UPDATE Combustivel SET
        tipo = p_tipo 
    WHERE idCombustivel = p_idCombustivel; 
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deleteCombustivel (
    IN p_idCombustivel INT )
BEGIN
    DELETE FROM Combustivel WHERE
        idCombustivel = p_idCombustivel ; 
END $ DELIMITER;

DELIMITER $ CREATE VIEW getCombustiveis AS
    SELECT
        idCombustivel AS 'ID do Combustível',
        idUsuario AS 'ID do Usuário',
        tipo AS 'Combustível'
    FROM Combustivel
    ORDER BY idUsuario, tipo;
$ DELIMITER;

-- -----------------------------------------------------
-- Posto
-- -----------------------------------------------------

CREATE TABLE Posto (
    idPosto INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    nome VARCHAR(32) NOT NULL,
    marca VARCHAR(16) NOT NULL,
    endereco VARCHAR(32),
    telefone VARCHAR(16),
    cartao BOOLEAN NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setPosto (
    IN p_idUsuario INT,
    IN p_nome VARCHAR(32),
    IN p_marca VARCHAR(16),
    IN p_endereco VARCHAR(32),
    IN p_telefone VARCHAR(16),
    IN p_cartao BOOLEAN )
BEGIN
    INSERT INTO Posto VALUES (
        NULL,
        p_idUsuario,
        p_nome,
        p_marca,
        p_endereco,
        p_telefone,
        p_cartao );
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updatePosto (
    IN p_idPosto INT,
    IN p_nome VARCHAR(32),
    IN p_marca VARCHAR(16),
    IN p_endereco VARCHAR(32),
    IN p_telefone VARCHAR(16),
    IN p_cartao BOOLEAN )
BEGIN
    UPDATE Posto SET
        nome = p_nome,
        marca = p_marca,
        endereco = p_endereco,
        telefone = p_telefone,
        cartao = p_cartao
    WHERE idPosto = p_idPosto ; 
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deletePosto (
    IN p_idPosto INT )
BEGIN
    DELETE FROM Posto WHERE
        idPosto = p_idPosto ; 
END $ DELIMITER;

DELIMITER $
CREATE VIEW getPostos AS
    SELECT
        idPosto AS 'ID do Posto',
        idUsuario AS 'ID do Usuário',
        nome AS 'Nome',
        marca AS 'Marca do Combustível',
        endereco AS 'Endereço',
        telefone AS 'Telefone',
        cartao AS 'Aceita Cartão?'
    FROM Posto
    ORDER BY idUsuario, nome;
$ DELIMITER;

-- -----------------------------------------------------
-- Abastecimento
-- -----------------------------------------------------

CREATE TABLE Abastecimento (
    idAbastecimento INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idAutomovel INT NOT NULL,
    data DATE NOT NULL,
    idCombustivel INT NOT NULL,
    odometro DOUBLE NOT NULL,
    litros DOUBLE NOT NULL,
    valorTotal DOUBLE NOT NULL,
    idPosto INT NOT NULL NOT NULL,
    anotacoes VARCHAR(256),
    FOREIGN KEY (idAutomovel) REFERENCES Automovel(idAutomovel) 
        ON DELETE CASCADE,
    FOREIGN KEY (idCombustivel) REFERENCES Combustivel(idCombustivel)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setAbastecimento (
    IN p_idAutomovel INT,
    IN p_data DATE,
    IN p_idCombustivel INT,
    IN p_odometro DOUBLE,
    IN p_litros DOUBLE,
    IN p_valorTotal DOUBLE,
    IN p_idPosto INT,
    IN p_anotacoes VARCHAR(256) )
BEGIN
    INSERT INTO Abastecimento VALUES (
        NULL,
        p_idAutomovel,
        p_data,
        p_idCombustivel,
        p_odometro,
        p_litros,
        p_valorTotal,
        p_idPosto,
        p_anotacoes );
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updateAbastecimento (
    IN p_idAbastecimento INT,
    IN p_idAutomovel INT,
    IN p_data DATE,
    IN p_idCombustivel INT,
    IN p_odometro DOUBLE,
    IN p_litros DOUBLE,
    IN p_valorTotal DOUBLE,
    IN p_idPosto INT,
    IN p_anotacoes VARCHAR(256) )
BEGIN
    UPDATE Abastecimento SET
        idAutomovel = p_idAutomovel,
        data = p_data,
        idCombustivel = p_idCombustivel,
        odometro = p_odometro,
        litros = p_litros,
        valorTotal = p_valorTotal,
        idPosto = p_idPosto,
        anotacoes = p_anotacoes 
    WHERE idAbastecimento = p_idAbastecimento ;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deleteAbastecimento (
    IN p_idAbastecimento INT )
BEGIN
    DELETE FROM Abastecimento WHERE
        idAbastecimento = p_idAbastecimento ; 
END $ DELIMITER;

DELIMITER $ CREATE VIEW getAbastecimentos AS
    SELECT
        idAbastecimento AS 'ID do Abastecimento',
        Automovel.idAutomovel AS 'ID do Automóvel',
        data AS 'Data',
        Combustivel.idCombustivel AS 'ID do Combustível',
        odometro AS 'Odômetro',
        litros AS 'Litros',
        valorTotal AS 'Valor Total',
        Posto.idPosto AS 'ID do Posto',
        anotacoes AS 'Anotações'
    FROM Abastecimento
    JOIN Automovel ON Abastecimento.idAutomovel = Automovel.idAutomovel
    JOIN Combustivel ON Abastecimento.idCombustivel = Combustivel.idCombustivel
    JOIN Posto ON Abastecimento.idPosto = Posto.idPosto
    ORDER BY Automovel.idAutomovel ASC;
$ DELIMITER;

-- -----------------------------------------------------
-- Troca de Óleo
-- -----------------------------------------------------

CREATE TABLE TrocaDeOleo (
    idTrocaDeOleo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idAutomovel INT NOT NULL,
    nomeOleo VARCHAR(32),
    tipoOleo VARCHAR(16),
    dataTroca DATE NOT NULL,
    dataProximaTroca DATE NOT NULL,
    trocaFiltro BOOLEAN NOT NULL,
    preco DOUBLE NOT NULL,
    FOREIGN KEY (idAutomovel) REFERENCES Automovel(idAutomovel)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setTrocaDeOleo (
    IN p_idAutomovel INT,
    IN p_nomeOleo VARCHAR(32),
    IN p_tipoOleo VARCHAR(16),
    IN p_dataTroca DATE,
    IN p_dataProximaTroca DATE,
    IN p_trocaFiltro BOOLEAN,
    IN p_preco DOUBLE )
BEGIN
    INSERT INTO TrocaDeOleo VALUES (
        NULL,
        p_idAutomovel,
        p_nomeOleo,
        p_tipoOleo,
        p_dataTroca,
        p_dataProximaTroca,
        p_trocaFiltro,
        p_preco );
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updateTrocaDeOleo (
    IN p_idTrocaDeOleo INT,
    IN p_nomeOleo VARCHAR(32),
    IN p_tipoOleo VARCHAR(16),
    IN p_dataTroca DATE,
    IN p_dataProximaTroca DATE,
    IN p_trocaFiltro BOOLEAN,
    IN p_preco DOUBLE )
BEGIN
    UPDATE TrocaDeOleo SET
        nomeOleo = p_nomeOleo,
        tipoOleo = p_tipoOleo,
        dataTroca = p_dataTroca,
        dataProximaTroca = p_dataProximaTroca,
        trocaFiltro = p_trocaFiltro,
        preco = p_preco
    WHERE idTrocaDeOleo = p_idTrocaDeOleo ;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deleteTrocaDeOleo (
    IN p_idTrocaDeOleo INT )
BEGIN
    DELETE FROM TrocaDeOleo WHERE
        idTrocaDeOleo = p_idTrocaDeOleo ; 
END $ DELIMITER;

DELIMITER $ CREATE VIEW getTrocasDeOleo AS
    SELECT
        idTrocaDeOleo AS 'ID da Troca de Óleo',
        idAutomovel AS 'ID do Automóvel', 
        nomeOleo AS 'Nome do Óleo',
        tipoOleo AS 'Tipo de Óleo',
        dataTroca AS 'Data da Troca',
        dataProximaTroca AS 'Data da Próxima Troca', 
        trocaFiltro AS 'Troca de Filtro?', 
        preco AS 'Preço'
    FROM TrocaDeOleo
    ORDER BY idAutomovel ASC;
$ DELIMITER;

-- -----------------------------------------------------
-- Lembretes
-- -----------------------------------------------------

CREATE TABLE Lembrete (
    idLembrete INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idAutomovel INT NOT NULL,
    dataPrevista DATE NOT NULL,
    valorOrcado DOUBLE,
    descricaoLocal VARCHAR(128),
    descricaoServico VARCHAR(255) NOT NULL,
    FOREIGN KEY (idAutomovel) REFERENCES Automovel(idAutomovel)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setLembrete (
    IN p_idAutomovel INT,
    IN p_dataPrevista DATE,
    IN p_valorOrcado DOUBLE,
    IN p_descricaoLocal VARCHAR(128),
    IN p_descricaoServico VARCHAR(255) )
BEGIN
    INSERT INTO Lembrete VALUES (
        NULL,
        p_idAutomovel,
        p_dataPrevista,
        p_valorOrcado,
        p_descricaoLocal,
        p_descricaoServico );
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updateLembrete (
    IN p_idLembrete INT,
    IN p_dataPrevista DATE,
    IN p_valorOrcado DOUBLE,
    IN p_descricaoLocal VARCHAR(128),
    IN p_descricaoServico VARCHAR(256) )
BEGIN
    UPDATE Lembrete SET
        dataPrevista = p_dataPrevista,
        valorOrcado = p_valorOrcado,
        descricaoLocal = p_descricaoLocal,
        descricaoServico = p_descricaoServico
    WHERE idLembrete = p_idLembrete ;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deleteLembrete (
    IN p_idLembrete INT )
BEGIN
    DELETE FROM Lembrete WHERE
        idLembrete = p_idLembrete ; 
END $ DELIMITER;

DELIMITER $ CREATE VIEW getLembretes AS
    SELECT
        idLembrete AS 'ID do Lembrete',
        idAutomovel AS 'ID do Automóvel',
        dataPrevista AS 'Data Prevista',
        valorOrcado AS 'Valor Orçado',
        descricaoLocal AS 'Descrição do Local',
        descricaoServico AS 'Descrição do Serviço'
    FROM Lembrete
    ORDER BY idAutomovel ASC;
$ DELIMITER;

-- -----------------------------------------------------
-- Serviços
-- -----------------------------------------------------

CREATE TABLE Servico (
    idServico INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idAutomovel INT NOT NULL,
    data DATE NOT NULL,
    descricao VARCHAR(256) NOT NULL,
    valorGasto DOUBLE NOT NULL,
    FOREIGN KEY (idAutomovel) REFERENCES Automovel(idAutomovel)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setServico (
    IN p_idAutomovel INT,
    IN p_data DATE,
    IN p_descricao VARCHAR(256),
    IN p_valorGasto DOUBLE )
BEGIN
    INSERT INTO Servico VALUES (
        NULL,
        p_idAutomovel,
        p_data,
        p_descricao,
        p_valorGasto );
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updateServico (
    IN p_idServico INT,
    IN p_data DATE,
    IN p_descricao VARCHAR(256),
    IN p_valorGasto DOUBLE )
BEGIN
    UPDATE Servico SET
        data = p_data,
        descricao = p_descricao,
        valorGasto = p_valorGasto
    WHERE idServico = p_idServico ;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deleteServico (
    IN p_idServico INT )
BEGIN
    DELETE FROM Servico WHERE
        idServico = p_idServico ; 
END $ DELIMITER;

DELIMITER $ CREATE VIEW getServicos AS
    SELECT
        idServico AS 'ID do Serviço',
        idAutomovel AS 'ID do Automóvel',
        data AS 'Data',
        descricao AS 'Descrição',
        valorGasto AS 'Valor Gasto'
    FROM Servico
    ORDER BY idAutomovel ASC;
$ DELIMITER;

-- -----------------------------------------------------
-- Seguros
-- -----------------------------------------------------

CREATE TABLE Seguro (
    idSeguro INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idAutomovel INT NOT NULL,
    inicioVigencia DATE NOT NULL,
    fimVigencia DATE NOT NULL,
    valorParcela DOUBLE NOT NULL,
    quantidadeParcelas INT NOT NULL,
    valorFranquia DOUBLE NOT NULL,
    valorCobertura DOUBLE NOT NULL,
    anotacoes VARCHAR(256),
    FOREIGN KEY (idAutomovel) REFERENCES Automovel(idAutomovel)
        ON DELETE CASCADE
);

DELIMITER $ CREATE PROCEDURE setSeguro (
    IN p_idAutomovel INT,
    IN p_inicioVigencia DATE,
    IN p_fimVigencia DATE,
    IN p_valorParcela DOUBLE,
    IN p_quantidadeParcelas INT,
    IN p_valorFranquia DOUBLE,
    IN p_valorCobertura DOUBLE,
    IN p_anotacoes VARCHAR(256) )
BEGIN
    INSERT INTO Seguro VALUES (
        NULL,
        p_idAutomovel,
        p_inicioVigencia,
        p_fimVigencia,
        p_valorParcela,
        p_quantidadeParcelas,
        p_valorFranquia,
        p_valorCobertura,
        p_anotacoes );
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE updateSeguro (
    IN p_idSeguro INT,
    IN p_inicioVigencia DATE,
    IN p_fimVigencia DATE,
    IN p_valorParcela DOUBLE,
    IN p_quantidadeParcelas INT,
    IN p_valorFranquia DOUBLE,
    IN p_valorCobertura DOUBLE,
    IN p_anotacoes VARCHAR(256) )
BEGIN
    UPDATE Seguro SET
        inicioVigencia = p_inicioVigencia,
        fimVigencia = p_fimVigencia,
        valorParcela = p_valorParcela,
        quantidadeParcelas = p_quantidadeParcelas,
        valorFranquia = p_valorFranquia,
        valorCobertura = p_valorCobertura,
        anotacoes = p_anotacoes
    WHERE idSeguro = p_idSeguro ;
END $ DELIMITER;

DELIMITER $ CREATE PROCEDURE deleteSeguro (
    IN p_idSeguro INT )
BEGIN
    DELETE FROM Seguro WHERE
        idSeguro = p_idSeguro ; 
END $ DELIMITER;

DELIMITER $ CREATE VIEW getSeguros AS
    SELECT
        idSeguro AS 'ID do Seguro',
        idAutomovel AS 'ID do Automóvel',
        inicioVigencia AS 'Data do Início da Vigência',
        fimVigencia AS 'Data do Fim da Vigência',
        valorParcela AS 'Valor da Parcela',
        quantidadeParcelas AS 'Quantidade de Parcelas',
        valorFranquia AS 'Valor da Franquia',
        valorCobertura AS 'Valor da Cobertura',
        anotacoes AS 'Anotações'
    FROM Seguro
    ORDER BY idAutomovel ASC;
$ DELIMITER;