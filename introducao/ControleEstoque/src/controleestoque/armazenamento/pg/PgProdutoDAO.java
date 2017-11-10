/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.ProdutoDAO;
import controleestoque.entidades.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class PgProdutoDAO implements ProdutoDAO {

    @Override
    public Produto buscar(Produto produto) {
        try {
            PreparedStatement ps = PostgreSqlDAOFactory.getConnection().prepareStatement(
                    "select codigo, nome, preco from produto where codigo = ?");
            ps.setLong(1, produto.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                long codigo = rs.getLong(1);
                String nome = rs.getString(2);
                double preco = rs.getDouble(3);
                Produto produtoEncontrado = new Produto(codigo, nome, preco);
                
                return produtoEncontrado;
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro ao obter registro de produto.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }

    @Override
    public boolean inserir(Produto produto) {
        try {
            PreparedStatement ps = PostgreSqlDAOFactory.getConnection()
                    .prepareStatement(
                            "insert into produto (nome, preco) values (?, ?)");
            
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro ao inserir registro de produto.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean alterar(Produto produto) {
        try {
            PreparedStatement ps = PostgreSqlDAOFactory.getConnection()
                    .prepareStatement(
                            "update produto set nome = ?, preco = ? where codigo = ?");
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setLong(3, produto.getCodigo());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro ao alterar registro de produto.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean excluir(Produto produto) {
        try {
            PreparedStatement ps = PostgreSqlDAOFactory.getConnection()
                    .prepareStatement("delete from produto where codigo = ?");
            ps.setLong(1, produto.getCodigo());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro ao excluir registro de produto.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    private ArrayList<Produto> listaProdutos;

    @Override
    public ArrayList<Produto> getLista() {
        if (listaProdutos == null)
            listaProdutos = new ArrayList<>();
        else
            listaProdutos.clear();
        
        try {
            PreparedStatement ps = PostgreSqlDAOFactory.getConnection()
                    .prepareStatement(
                            "select codigo, nome, preco from produto order by nome");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                long codigo = rs.getLong(1);
                String nome = rs.getString(2);
                double preco = rs.getDouble(3);
                Produto p = new Produto(codigo, nome, preco);
                listaProdutos.add(p);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro ao obter registros de produtos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaProdutos;
    }
    
}
