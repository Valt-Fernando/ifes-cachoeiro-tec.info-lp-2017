/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.ItemCompraDAO;
import controleestoque.entidades.Compra;
import controleestoque.entidades.ItemCompra;
import controleestoque.entidades.Produto;
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
public class PgItemCompraDAO implements ItemCompraDAO {
    
    private static final String SCRIPT_BUSCAR = 
            "select i.codigo, i.produto, i.precocompra, i.quantidade, " +
            "       p.nome, p.preco " +
            "  from itemcompra i " +
            "       join produto p on (i.produto = p.codigo) " +
            " where i.codigo = ?";
    
    private static final String SCRIPT_INSERIR = 
            "insert into itemcompra " +
            "        (compra, produto, precocompra, quantidade) " +
            " values (?, ?, ?, ?)";

    private static final String SCRIPT_ALTERAR = 
            "update itemcompra " +
            "   set produto = ?, " +
            "       precocompra = ?, " +
            "       quantidade = ? " +
            " where codigo = ?";

    private static final String SCRIPT_EXCLUIR = 
            "delete from itemcompra where codigo = ?";

    private static final String SCRIPT_GET_LISTA = 
            "select i.codigo, i.produto, i.precocompra, i.quantidade, " +
            "       p.nome, p.preco " +
            "  from itemcompra i " +
            "       join produto p on (i.produto = p.codigo) " +
            " where i.compra = ?";
    
    @Override
    public ItemCompra buscar(ItemCompra itemCompra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            ps.setLong(1, itemCompra.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                long codigoItem = rs.getLong(1); // == itemCompra.getCodigo()
                long codigoProduto = rs.getLong(2);
                double precoCompra = rs.getDouble(3);
                int quantidade = rs.getInt(4);
                String nomeProduto = rs.getString(5);
                double precoProduto = rs.getDouble(6);
                
                // criar objeto Produto
                Produto produto = new Produto(codigoProduto, nomeProduto, precoProduto);
                
                // criar objeto ItemCompra
                ItemCompra itemBuscado = new ItemCompra(
                        codigoItem, 
                        itemCompra.getCompra(), 
                        produto, 
                        precoCompra, 
                        quantidade);
                
                return itemBuscado;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registro de item de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean inserir(ItemCompra itemCompra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR);
            ps.setLong(1, itemCompra.getCompra().getCodigo());
            ps.setLong(2, itemCompra.getProduto().getCodigo());
            ps.setDouble(3, itemCompra.getPrecoCompra());
            ps.setInt(4, itemCompra.getQuantidade());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao inserir registro de item de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean alterar(ItemCompra itemCompra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao alterar registro de item de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean excluir(ItemCompra itemCompra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao excluir registro de item de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private ArrayList<ItemCompra> listaItemCompra;
    
    @Override
    public ArrayList<ItemCompra> getLista(Compra compra) {
        if (listaItemCompra == null)
            listaItemCompra = new ArrayList<>();
        else
            listaItemCompra.clear();
        
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registros de itens de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaItemCompra;
    }
    
}
