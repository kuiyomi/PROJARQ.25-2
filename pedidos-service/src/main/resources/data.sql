insert into clientes (cpf, nome, celular, endereco, email)
values ('123.456.789-00', 'Fulano', '51999999999', 'Rua X', 'cliente@teste.com');

insert into ingredientes (id, descricao) values (1,'Disco de pizza');
insert into ingredientes (id, descricao) values (2,'Porcao de tomate');
insert into ingredientes (id, descricao) values (3,'Porcao de mussarela');
insert into ingredientes (id, descricao) values (4,'Porcao de presunto');
insert into ingredientes (id, descricao) values (5,'Porcao de calabresa');
insert into ingredientes (id, descricao) values (6,'Molho de tomate');
insert into ingredientes (id, descricao) values (7,'Porcao de azeitona');
insert into ingredientes (id, descricao) values (8,'Porcao de oregano');
insert into ingredientes (id, descricao) values (9,'Porcao de cebola');

insert into produtos (id, descricao, preco) values (1,'Pizza Mussarela',45);
insert into produtos (id, descricao, preco) values (2,'Pizza Calabresa',48);
insert into produtos (id, descricao, preco) values (3,'Pizza Presunto',47);

insert into receitas (id, titulo) values (1,'Receita Mussarela');
insert into receitas (id, titulo) values (2,'Receita Calabresa');
insert into receitas (id, titulo) values (3,'Receita Presunto');

insert into produto_receita (produto_id, receita_id) values (1,1);
insert into produto_receita (produto_id, receita_id) values (2,2);
insert into produto_receita (produto_id, receita_id) values (3,3);

-- Mussarela
insert into receita_ingrediente values (1,1);
insert into receita_ingrediente values (1,6);
insert into receita_ingrediente values (1,3);
insert into receita_ingrediente values (1,8);

-- Calabresa
insert into receita_ingrediente values (2,1);
insert into receita_ingrediente values (2,6);
insert into receita_ingrediente values (2,5);
insert into receita_ingrediente values (2,9);
insert into receita_ingrediente values (2,8);

-- Presunto
insert into receita_ingrediente values (3,1);
insert into receita_ingrediente values (3,6);
insert into receita_ingrediente values (3,4);
insert into receita_ingrediente values (3,3);
insert into receita_ingrediente values (3,8);

insert into cardapios values (1,'Cardapio Principal');
insert into cardapio_produto values (1,1);
insert into cardapio_produto values (1,2);
insert into cardapio_produto values (1,3);
