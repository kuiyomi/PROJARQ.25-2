-- CLIENTE de teste (bate com o login do gateway, se quiser)
insert into clientes (nome, cpf, celular, endereco, email)
values ('Fulano da Silva', '123.456.789-00', '51999999999', 'Rua X, 123', 'cliente@teste.com');

-- INGREDIENTES (mesmos IDs do estoque-service para manter coerência)
insert into ingredientes (id, descricao) values (1, 'Disco de pizza');
insert into ingredientes (id, descricao) values (2, 'Porcao de tomate');
insert into ingredientes (id, descricao) values (3, 'Porcao de mussarela');
insert into ingredientes (id, descricao) values (4, 'Porcao de presunto');
insert into ingredientes (id, descricao) values (5, 'Porcao de calabresa');
insert into ingredientes (id, descricao) values (6, 'Molho de tomate (200ml)');
insert into ingredientes (id, descricao) values (7, 'Porcao de azeitona');
insert into ingredientes (id, descricao) values (8, 'Porcao de oregano');
insert into ingredientes (id, descricao) values (9, 'Porcao de cebola');

-- PRODUTOS
insert into produtos (id, descricao, preco) values (1, 'Pizza Mussarela', 45.00);
insert into produtos (id, descricao, preco) values (2, 'Pizza Calabresa', 48.00);
insert into produtos (id, descricao, preco) values (3, 'Pizza Presunto', 47.00);

-- MAPEAMENTO PRODUTO x INGREDIENTE (ajuste se teu JDBC usar outra tabela)
-- Pizza Mussarela: disco + molho + mussarela + oregano
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (1, 1, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (1, 6, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (1, 3, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (1, 8, 1);

-- Pizza Calabresa: disco + molho + calabresa + cebola + oregano
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (2, 1, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (2, 6, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (2, 5, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (2, 9, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (2, 8, 1);

-- Pizza Presunto: disco + molho + presunto + mussarela + oregano
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (3, 1, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (3, 6, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (3, 4, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (3, 3, 1);
insert into produto_ingrediente (produto_id, ingrediente_id, quantidade) values (3, 8, 1);

-- CARDÁPIO
insert into cardapios (titulo, ativo) values ('Cardapio Principal', true);
-- coloque os 3 produtos no cardápio 1:
insert into cardapio_produto (cardapio_id, produto_id) values (1, 1);
insert into cardapio_produto (cardapio_id, produto_id) values (1, 2);
insert into cardapio_produto (cardapio_id, produto_id) values (1, 3);
