/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.ItemVendaDAO;
import controleestoque.entidades.ItemCompra;
import controleestoque.entidades.ItemVenda;
import controleestoque.entidades.Produto;
import controleestoque.entidades.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class PgItemVendaDAO implements ItemVendaDAO {
    
    private static final String SCRIPT_BUSCAR = 
            "select i.codigo, i.produto, i.precovenda, i.quantidade, " +
            "       p.nome, p.preco " +
            "  from itemvenda i " +
            "       join produto p on (i.produto = p.codigo) " +
            " where i.codigo = ?";
    
    private static final String SCRIPT_INSERIR = 
            "insert into itemvenda " +
            "        (venda, produto, precovenda, quantidade) " +
            " values (?, ?, ?, ?)";

    private static final String SCRIPT_ALTERAR = 
            "update itemvenda " +
            "   set produto = ?, " +
            "       precovenda = ?, " +
            "       quantidade = ? " +
            " where codigo = ?";

    private static final String SCRIPT_EXCLUIR = 
            "delete from itemvenda where codigo = ?";

    private static final String SCRIPT_GET_LISTA = 
            "select i.codigo, i.produto, i.precovenda, i.quantidade, " +
            "       p.nome, p.preco " +
            "  from itemvenda i " +
            "       join produto p on (i.produto = p.codigo) " +
            " where i.venda = ?";
    
    @Override
    public ItemVenda buscar(ItemVenda itemVenda) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            ps.setLong(1, itemVenda.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                long codigoItem = rs.getLong(1); // == itemVenda.getCodigo()
                long codigoProduto = rs.getLong(2);
                double precoVenda = rs.getDouble(3);
                int quantidade = rs.getInt(4);
                String nomeProduto = rs.getString(5);
                double precoProduto = rs.getDouble(6);
                
                // criar objeto Produto
                Produto produto = new Produto(codigoProduto, nomeProduto, precoProduto);
                
                // criar objeto ItemVenda
                ItemVenda itemBuscado = new ItemVenda(
                        codigoItem, 
                        itemVenda.getVenda(), 
                        produto, 
                        precoVenda, 
                        quantidade);
                
                return itemBuscado;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registro de item de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean inserir(ItemVenda itemVenda) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR);
            ps.setLong(1, itemVenda.getVenda().getCodigo());
            ps.setLong(2, itemVenda.getProduto().getCodigo());
            ps.setDouble(3, itemVenda.getPrecoVenda());
            ps.setInt(4, itemVenda.getQuantidade());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao inserir registro de item de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean alterar(ItemVenda itemVenda) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_ALTERAR);
            ps.setLong(1, itemVenda.getProduto().getCodigo());
            ps.setDouble(2, itemVenda.getPrecoVenda());
            ps.setInt(3, itemVenda.getQuantidade());
            ps.setLong(4, itemVenda.getCodigo());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao alterar registro de item de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean excluir(ItemVenda itemVenda) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_EXCLUIR);
            ps.setLong(1, itemVenda.getCodigo());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao excluir registro de item de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private ArrayList<ItemVenda> listaItemVenda;
    
    @Override
    public ArrayList<ItemVenda> getLista(Venda venda) {
        if (listaItemVenda == null)
            listaItemVenda = new ArrayList<>();
        else
            listaItemVenda.clear();
        
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_GET_LISTA);
            ps.setLong(1, venda.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                long codigoItem = rs.getLong(1);
                long codigoProduto = rs.getLong(2);
                double precoVenda = rs.getDouble(3);
                int quantidade = rs.getInt(4);
                String nomeProduto = rs.getString(5);
                double precoProduto = rs.getDouble(6);
                
                Produto produto = new Produto(codigoProduto, nomeProduto, precoProduto);
                
                ItemVenda item = new ItemVenda(codigoItem, venda, produto, 
                        precoVenda, quantidade);
                
                listaItemVenda.add(item);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registros de itens de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaItemVenda;
    }
    
}
