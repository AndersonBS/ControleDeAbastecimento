-- -----------------------------------------------------
-- Database
-- -----------------------------------------------------

USE PW2_TrabalhoFinalDB;

-- -----------------------------------------------------
-- Stored Procedures
-- -----------------------------------------------------

CALL setUsuario('Anderson Sensolo', 'anderson.sensolo@gmail.com', 'anderson123', null);
CALL setUsuario('Teste', 'teste@gmail.com', 'teste123', null);

CALL setAutomovel(1, 'Gol', 'Volkswagen', 2008, '2008-10-13', 'ABC1234');
CALL setAutomovel(1, 'Uno', 'Fiat', 2010, '2010-05-17', 'EFG2345');
CALL setAutomovel(1, 'Celta', 'Chevrolet', 2012, '2012-01-01', 'IJK3456');
CALL setAutomovel(1, 'S10', 'Chevrolet', 2015, '2015-03-30', 'MNO4567');
CALL setAutomovel(2, 'Teste', 'Teste', 2015, '2015-03-30', 'MNO4567');

CALL setCombustivel(1, 'Gasolina Comum');
CALL setCombustivel(1, 'Gasolina Aditivada');
CALL setCombustivel(1, 'Etanol');
CALL setCombustivel(1, 'Diesel');
CALL setCombustivel(1, 'Biodiesel');
CALL setCombustivel(1, 'Gás');
CALL setCombustivel(2, 'Teste');

CALL setPosto(1, 'América', 'AmericanOil', 'R Cruz e Souza, 11', '(49)9926-2159', true);
CALL setPosto(1, 'Bostoli', 'Ipiranga', 'R. Santos Dumont, 1090', '(49)2681-2589', false);
CALL setPosto(1, 'Age', 'Ipiranga', 'Av. XV de Novembro, 528', '(49)9254-2516', true);
CALL setPosto(1, 'Cruzeiro', 'Ipiranga', 'Av Br Rio Branco 336', '(49)9203-1168', true);
CALL setPosto(1, 'São Cristóvão', 'Esso', 'Rod BR-282, 391', '(49)2618-2668', false);
CALL setPosto(1, 'Amigão', 'BR', 'Av Santa Terezinha, 3010', '(49)6281-1284', true);
CALL setPosto(2, 'Teste', 'Teste', 'Av Teste', '(99)9999-9999', false);

CALL setAbastecimento(1, '2015-03-30', 1, 128.02, 39.60, 49.99, 3, 'Primeiro abastecimento do Gol!');
CALL setAbastecimento(2, '2015-03-29', 2, 258.37, 25.15, 35.57, 2, 'Primeiro abastecimento do Uno!');
CALL setAbastecimento(3, '2015-04-28', 1, 123.58, 17.32, 51.99, 1, 'Primeiro abastecimento do Celta!');
CALL setAbastecimento(3, '2015-05-15', 1, 142.25, 19.84, 62.00, 1, 'Segundo abastecimento do Celta!');
CALL setAbastecimento(3, '2015-06-09', 2, 227.98, 25.24, 81.29, 1, 'Terceiro abastecimento do Celta!');
CALL setAbastecimento(3, '2015-06-23', 3, 155.08, 25.00, 47.25, 1, 'Quarto abastecimento do Celta!');
CALL setAbastecimento(4, '2015-03-31', 4, 250.00, 65.10, 75.13, 4, 'Primeiro abastecimento da S10!');
CALL setAbastecimento(4, '2015-04-01', 4, 337.39, 25.00, 54.00, 5, 'Segundo abastecimento da S10!');

CALL setTrocaDeOleo(3, 'Lubrax', 'Míneral', '2010-03-28', '2014-03-28', true, 157.99);
CALL setTrocaDeOleo(3, 'Lubrax', 'Sintético', '2014-03-28', '2018-03-28', false, 200.99);
CALL setTrocaDeOleo(2, 'Lubrax', 'Semissintético', '2015-03-28', '2019-03-28', true, 90.99);

CALL setLembrete(3, '2015-03-28', 50.00, 'Concessionária', 'Rodízio de Peneus');
CALL setLembrete(3, '2016-05-01', 400.00, 'Concessionária', 'Troca de Peneus');
CALL setLembrete(2, '2015-03-28', 76.55, 'Chapearia', 'Martelinho de Ouro');

CALL setServico(3, '2015-03-28', 'Rodízio de Peneus', 50.00);
CALL setServico(3, '2016-01-17', 'Troca de Peneus', 400.00);
CALL setServico(2, '2015-03-28', 'Martelinho de Ouro', 76.55);

CALL setSeguro(3, '2015-03-28', '2016-03-28', 100.00, 12, 5000, 250000, 'Tel: (49)9999-9999');
CALL setSeguro(2, '2015-03-28', '2016-03-28', 200.00, 12, 10000, 350000, 'Tel: (49)9999-9999');


-- -----------------------------------------------------
-- Views
-- -----------------------------------------------------

SELECT * FROM getAbastecimentos;
SELECT * FROM getAutomoveis;
SELECT * FROM getCombustiveis;
SELECT * FROM getPostos;
SELECT * FROM getTrocasDeOleo;
SELECT * FROM getLembretes;
SELECT * FROM getServicos;
SELECT * FROM getSeguros;
SELECT * FROM Usuario;