<<<<<<< HEAD
<<<<<<< HEAD
-- Inserção dos clientes
INSERT INTO clientes (cpf, nome, celular, endereco, email) VALUES ('9001', 'Huguinho Pato', '51985744566', 'Rua das Flores, 100', 'huguinho.pato@email.com');
INSERT INTO clientes (cpf, nome, celular, endereco, email) VALUES ('9002', 'Luizinho Pato', '5199172079', 'Av. Central, 200', 'zezinho.pato@email.com');

-- Inserção dos ingredientes
INSERT INTO ingredientes (id, descricao) VALUES (1, 'Disco de pizza');
INSERT INTO ingredientes (id, descricao) VALUES (2, 'Porcao de tomate');
INSERT INTO ingredientes (id, descricao) VALUES (3, 'Porcao de mussarela');
INSERT INTO ingredientes (id, descricao) VALUES (4, 'Porcao de presunto');
INSERT INTO ingredientes (id, descricao) VALUES (5, 'Porcao de calabresa');
INSERT INTO ingredientes (id, descricao) VALUES (6, 'Molho de tomate (200ml)');
INSERT INTO ingredientes (id, descricao) VALUES (7, 'Porcao de azeitona');
INSERT INTO ingredientes (id, descricao) VALUES (8, 'Porcao de oregano');
INSERT INTO ingredientes (id, descricao) VALUES (9, 'Porcao de cebola');

-- Inserção dos itens de estoque (estes dados não são realmente usados no pedidos-service, mas mantidos para consistência do schema)
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (1, 30, 1);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (2, 30, 2);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (3, 30, 3);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (4, 30, 4);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (5, 30, 5);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (6, 30, 6);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (7, 30, 7);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (8, 30, 8);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (9, 30, 9);

-- Inserção das receitas 
INSERT INTO receitas (id, titulo) VALUES (1, 'Pizza calabresa');
INSERT INTO receitas (id, titulo) VALUES (2, 'Pizza queijo e presunto');
INSERT INTO receitas (id, titulo) VALUES (3, 'Pizza margherita');

-- Associação dos ingredientes à receita Pizza calabresa
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 1); -- Disco de pizza
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 6); -- Molho de tomate (200ml)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 3); -- Porcao de mussarela
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 5); -- Porcao de calabresa
-- Associação dos ingredientes à receita Pizza queijo e presunto
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 1); -- Disco de pizza
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 6); -- Molho de tomate (200ml)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 3); -- Porcao de mussarela
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 4); -- Porcao de presunto
-- Associação dos ingredientes à receita Pizza margherita
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 1); -- Disco de pizza
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 6); -- Molho de tomate (200ml)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 3); -- Porcao de mussarela
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 8); -- Porcao de cebola

-- insercao dos produtos
INSERT INTO produtos (id,descricao,preco) VALUES (1,'Pizza calabresa',5500);
INSERT INTO produtos (id,descricao,preco) VALUES (2,'Pizza queijo e presunto',6000);
INSERT INTO produtos (id,descricao,preco) VALUES (3,'Pizza margherita',4000);

<<<<<<< HEAD
-- CARDÁPIO
insert into cardapios (titulo, ativo) values ('Cardapio Principal', true);
-- coloque os 3 produtos no cardápio 1:
insert into cardapio_produto (cardapio_id, produto_id) values (1, 1);
insert into cardapio_produto (cardapio_id, produto_id) values (1, 2);
insert into cardapio_produto (cardapio_id, produto_id) values (1, 3);
=======
-- Associação dos produtos com as receitas
INSERT INTO produto_receita (produto_id,receita_id) VALUES(1,1);
INSERT INTO produto_receita (produto_id,receita_id) VALUES(2,2);
INSERT INTO produto_receita (produto_id,receita_id) VALUES(3,3);

-- Insercao dos cardapios
INSERT INTO cardapios (id,titulo) VALUES(1,'Cardapio de Agosto');
INSERT INTO cardapios (id,titulo) VALUES(2,'Cardapio de Setembro');

-- Associação dos cardapios com os produtos
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,1);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,2);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,3);

INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (2,1);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (2,3);
>>>>>>> e9847fe (implementacao final (ou quase))
=======
=======
-- CLIENTE de teste
>>>>>>> 91f6cf2 (mais alguns ajustes, todos modulos e curl rodando)
insert into clientes (cpf, nome, celular, endereco, email)
values ('123.456.789-00', 'Fulano da Silva', '51999999999', 'Rua X, 123', 'cliente@teste.com');

-- INGREDIENTES (IDs fixos)
insert into ingredientes (id, descricao) values (1, 'Disco de pizza');
insert into ingredientes (id, descricao) values (2, 'Porcao de tomate');
insert into ingredientes (id, descricao) values (3, 'Porcao de mussarela');
insert into ingredientes (id, descricao) values (4, 'Porcao de presunto');
insert into ingredientes (id, descricao) values (5, 'Porcao de calabresa');
insert into ingredientes (id, descricao) values (6, 'Molho de tomate (200ml)');
insert into ingredientes (id, descricao) values (7, 'Porcao de azeitona');
insert into ingredientes (id, descricao) values (8, 'Porcao de oregano');
insert into ingredientes (id, descricao) values (9, 'Porcao de cebola');

-- PRODUTOS (preço em unidades inteiras)
insert into produtos (id, descricao, preco) values (1, 'Pizza Mussarela', 45);
insert into produtos (id, descricao, preco) values (2, 'Pizza Calabresa', 48);
insert into produtos (id, descricao, preco) values (3, 'Pizza Presunto', 47);

-- RECEITAS
insert into receitas (id, titulo) values (1, 'Receita Pizza Mussarela');
insert into receitas (id, titulo) values (2, 'Receita Pizza Calabresa');
insert into receitas (id, titulo) values (3, 'Receita Pizza Presunto');

-- PRODUTO x RECEITA
insert into produto_receita (produto_id, receita_id) values (1, 1);
insert into produto_receita (produto_id, receita_id) values (2, 2);
insert into produto_receita (produto_id, receita_id) values (3, 3);

-- RECEITA x INGREDIENTE
-- Pizza Mussarela: disco + molho + mussarela + oregano
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (1, 1, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (1, 6, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (1, 3, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (1, 8, 1);

-- Pizza Calabresa: disco + molho + calabresa + cebola + oregano
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (2, 1, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (2, 6, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (2, 5, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (2, 9, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (2, 8, 1);

-- Pizza Presunto: disco + molho + presunto + mussarela + oregano
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (3, 1, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (3, 6, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (3, 4, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (3, 3, 1);
insert into receita_ingrediente (receita_id, ingrediente_id, quantidade) values (3, 8, 1);

<<<<<<< HEAD
insert into cardapios values (1,'Cardapio Principal');
insert into cardapio_produto values (1,1);
insert into cardapio_produto values (1,2);
insert into cardapio_produto values (1,3);
>>>>>>> d83a0c4 (ajustando erros dos modulos nao subirem)
=======
-- CARDÁPIO
insert into cardapios (id, titulo, ativo) values (1, 'Cardapio Principal', true);

-- Produtos no cardápio 1
insert into cardapio_produto (cardapio_id, produto_id) values (1, 1);
insert into cardapio_produto (cardapio_id, produto_id) values (1, 2);
insert into cardapio_produto (cardapio_id, produto_id) values (1, 3);
>>>>>>> 91f6cf2 (mais alguns ajustes, todos modulos e curl rodando)
