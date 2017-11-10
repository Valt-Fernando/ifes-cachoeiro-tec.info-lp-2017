/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.ClienteDAO;
import controleestoque.armazenamento.CompraDAO;
import controleestoque.armazenamento.DAOFactory;
import controleestoque.armazenamento.FornecedorDAO;
import controleestoque.armazenamento.FuncionarioDAO;
import controleestoque.armazenamento.ItemCompraDAO;
import controleestoque.armazenamento.ItemVendaDAO;
import controleestoque.armazenamento.ProdutoDAO;
import controleestoque.armazenamento.VendaDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class PostgreSqlDAOFactory extends DAOFactory {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/estoque";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "romanelli";
    
    private static Connection con;
    
    public static Connection getConnection() {
        if (con == null) {
            try {
                
                Class.forName("org.postgresql.Driver");
                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível carregar o conector para PostgreSQL.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                        "Não foi possível conectar ao banco de dados.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return con;
    }

    @Override
    public ProdutoDAO getProdutoDAO() {
        return new PgProdutoDAO();
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new PgClienteDAO();
    }

    @Override
    public FornecedorDAO getFornecedorDAO() {
        return new PgFornecedorDAO();
    }

    @Override
    public FuncionarioDAO getFuncionarioDAO() {
        return new PgFuncionarioDAO();
    }

    @Override
    public CompraDAO getCompraDAO() {
        return new PgCompraDAO();
    }

    @Override
    public ItemCompraDAO getItemCompraDAO() {
        return new PgItemCompraDAO();
    }

    @Override
    public VendaDAO getVendaDAO() {
        return new PgVendaDAO();
    }

    @Override
    public ItemVendaDAO getItemVendaDAO() {
        return new PgItemVendaDAO();
    }
    
}
