/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.CompraDAO;
import controleestoque.armazenamento.DAOFactory;
import controleestoque.armazenamento.FuncionarioDAO;
import controleestoque.armazenamento.ItemCompraDAO;
import controleestoque.entidades.Compra;
import controleestoque.entidades.Comprador;
import controleestoque.entidades.Fornecedor;
import controleestoque.entidades.Funcionario;
import controleestoque.entidades.ItemCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class PgCompraDAO implements CompraDAO {
    
    private static final String SCRIPT_BUSCAR =
            "select codigo, datacompra, valortotal, comprador, fornecedor " +
            "  from compra " +
            " where codigo = ?";
    
    private static final String SCRIPT_INSERIR =
            "insert into compra (datacompra, valortotal, comprador, fornecedor) " +
            "values (?, ?, ?, ?)";
    
    private static final String SCRIPT_ALTERAR =
            "update compra " +
            "   set datacompra = ?, " +
            "       valortotal = ?, " +
            "       comprador = ?, " +
            "       fornecedor = ? " +
            " where codigo = ?";
    
    private static final String SCRIPT_EXCLUIR =
            "delete from compra where codigo = ?";
    
    private static final String SCRIPT_GET_LISTA =
            "select codigo, datacompra, valortotal, comprador, fornecedor " +
            "  from compra " +
            " order by datacompra desc";

    @Override
    public Compra buscar(Compra compra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            ps.setLong(1, compra.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long codigoCompra = rs.getLong(1);
                Date dataCompra = rs.getDate(2);
                double valorTotal = rs.getDouble(3);
                long codigoComprador = rs.getLong(4);
                long codigoFornecedor = rs.getLong(5);
                
                // construir objeto Comprador
                FuncionarioDAO funcionarioDAO = 
                        DAOFactory.getDefaultDAOFactory().getFuncionarioDAO();
                Funcionario f = new Funcionario(codigoComprador);
                Comprador comprador = (Comprador) funcionarioDAO.buscar(f);
                
                // construir objeto Fornecedor
                // obter objeto pelo FornecedorDAO
                Fornecedor fornecedor = null; // -----> corrigir para usar o DAO
                
                // obter lista de itens de compra
                ItemCompraDAO itemCompraDAO =
                        DAOFactory.getDefaultDAOFactory().getItemCompraDAO();
                Compra parametroCompra = new Compra(codigoCompra);
                ArrayList<ItemCompra> listaItemCompra = 
                        itemCompraDAO.getLista(parametroCompra);
                
                // construir objeto Compra
                Compra compraBuscada = new Compra(codigoCompra, dataCompra, 
                        comprador, fornecedor);
                for (ItemCompra item : listaItemCompra) {
                    item.setCompra(compraBuscada);
                    compraBuscada.inserirItemCompra(item);
                }
                
                return compraBuscada;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao buscar registro de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean inserir(Compra compra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao inserir registro de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean alterar(Compra compra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao alterar registro de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean excluir(Compra compra) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao excluir registro de compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private ArrayList<Compra> listaCompra;
    
    @Override
    public ArrayList<Compra> getLista() {
        // inicializa a lista, se ainda não tiver sido inicializada
        if (listaCompra == null)
            listaCompra = new ArrayList<>();
        // ou limpa a lista, se já tiver sido inicializada
        else
            listaCompra.clear();
        
        // procede a execução do SELECT no banco de dados
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao buscar registros de compras.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaCompra;
    }
    
}
