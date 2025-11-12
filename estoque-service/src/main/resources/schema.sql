create table if not exists ingredientes (
  id bigint primary key,
  descricao varchar(255) not null
);

create table if not exists itensEstoque (
  id bigint primary key,
  quantidade int not null,
  ingrediente_id bigint not null,
  constraint fk_estoque_ingrediente foreign key (ingrediente_id) references ingredientes(id)
);
