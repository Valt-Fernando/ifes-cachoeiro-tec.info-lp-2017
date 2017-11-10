create table Produto (
  codigo serial primary key,
  nome varchar(30) not null,
  preco double precision not null
);

create table Fornecedor (
  codigo serial primary key,
  nomefantasia varchar(30) not null,
  razaosocial varchar(100) not null,
  endereco varchar(200) not null,
  cnpj char(14) not null,
  inscricaoestadual varchar(9) not null,
  telefone varchar(17) not null,
  email varchar(50) not null
);

create table Cliente (
  codigo serial primary key,
  endereco varchar(200) not null,
  telefone varchar(17) not null,
  email varchar(50) not null
);

create table ClientePessoaFisica (
  codigo integer primary key,
  cpf char(11) not null,
  sexo char(1) not null,
  nome varchar(30) not null,
  datanascimento date not null,
  
  foreign key (codigo) 
    references Cliente(codigo)
);

create table ClientePessoaJuridica (
  codigo integer primary key,
  cnpj char(14) not null,
  inscricaoestadual varchar(9) not null,
  nomefantasia varchar(30) not null,
  razaosocial varchar(100) not null,
  
  foreign key (codigo)
    references Cliente(codigo)
);

create table Funcionario (
  codigo serial primary key,
  nome varchar(30) not null,
  cpf char(11) not null,
  endereco varchar(200) not null,
  telefone varchar(17) not null,
  email varchar(50) not null,
  cargo char(1) not null -- 'V': vendedor; 'C': comprador
);

create table Compra (
  codigo serial primary key,
  datacompra date not null,
  valortotal double precision not null,
  comprador integer not null,
  fornecedor integer not null,
  
  foreign key (comprador)
    references Funcionario(codigo),
    
  foreign key (fornecedor)
    references Fornecedor(codigo)
);

create table ItemCompra (
  codigo serial primary key,
  compra integer not null,
  produto integer not null,
  precocompra double precision not null,
  quantidade integer not null,
  
  foreign key (compra)
    references Compra(codigo),
    
  foreign key (produto)
    references Produto(codigo)
);

create table Venda (
  codigo serial primary key,
  datavenda date not null,
  valortotal double precision not null,
  vendedor integer not null,
  cliente integer not null,
  
  foreign key (vendedor)
    references Funcionario(codigo),
    
  foreign key (cliente)
    references Cliente(codigo)
);

create table ItemVenda (
  codigo serial primary key,
  venda integer not null,
  produto integer not null,
  precovenda double precision not null,
  quantidade integer not null,
  
  foreign key (venda)
    references Venda(codigo),
    
  foreign key (produto)
    references Produto(codigo)
);








