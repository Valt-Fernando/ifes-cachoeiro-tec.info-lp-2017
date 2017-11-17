-- obter um objeto Compra com seus objetos relacionados (Vendedor, Comprador)
select c.datacompra, c.valortotal, c.comprador,
       f.nome, f.cpf, f.endereco, f.telefone, f.email,
       c.fornecedor, fo.nomefantasia, fo.razaosocial,
       fo.endereco, fo.cnpj, fo.inscricaoestadual,
       fo.telefone, fo.email
  from Compra c
       join Funcionario f on (c.comprador = f.codigo)
       join Fornecedor fo on (c.fornecedor = fo.codigo)
 where c.codigo = ?

-- obter um objeto ItemCompra com seu objeto relacionado Produto
select i.codigo, i.produto, i.precocompra, i.quantidade,
       p.nome, p.preco
  from itemcompra i
       join produto p on (i.produto = p.codigo)
 where i.codigo = ?
