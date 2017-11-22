/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.CompraDAO;
import controleestoque.armazenamento.DAOFactory;
import controleestoque.armazenamento.FornecedorDAO;
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
import java.sql.Statement;
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
                FornecedorDAO fornecedorDAO = DAOFactory.getDefaultDAOFactory().getFornecedorDAO();
                Fornecedor fornecedorBusca = new Fornecedor(codigoFornecedor);
                Fornecedor fornecedor = fornecedorDAO.buscar(fornecedorBusca);
                
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
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR, Statement.RETURN_GENERATED_KEYS);
            java.sql.Date dataParaInserir = new java.sql.Date(compra.getData().getTime());
            ps.setDate(1, dataParaInserir);
            ps.setDouble(2, compra.getValorTotal());
            ps.setLong(3, compra.getComprador().getCodigo());
            ps.setLong(4, compra.getFornecedor().getCodigo());
            
            int resultado = ps.executeUpdate();
            
            if (resultado == 1) {
                // obtém o código gerado ao inserir registro
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                long codigo = rs.getLong(1);
                compra.setCodigo(codigo);
                
                // insere lista de itens da compra
                ItemCompraDAO itemCompraDAO = DAOFactory.getDefaultDAOFactory().getItemCompraDAO();
                for (ItemCompra item : compra.getItensCompra()) {
                    itemCompraDAO.inserir(item);
                }
            }
            
            return resultado == 1;
            
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
            PreparedStatement ps = con.prepareStatement(SCRIPT_ALTERAR);
            java.sql.Date dataParaAlterar = new java.sql.Date(compra.getData().getTime());
            ps.setDate(1, dataParaAlterar);
            ps.setDouble(2, compra.getValorTotal());
            ps.setLong(3, compra.getComprador().getCodigo());
            ps.setLong(4, compra.getFornecedor().getCodigo());
            ps.setLong(5, compra.getCodigo());
            
            int resultado = ps.executeUpdate();
            
            // alterar lista de itens
            if (resultado == 1) {
                ItemCompraDAO itemCompraDAO = DAOFactory.getDefaultDAOFactory().getItemCompraDAO();
                
                // excluir itens de compra antigos (talvez até sejam os mesmos. em outra versão, poderia ser feita uma verificação)
                ArrayList<ItemCompra> listaItensAntigos = itemCompraDAO.getLista(compra);
                for (ItemCompra item : listaItensAntigos) {
                    itemCompraDAO.excluir(item);
                }
                
                // inserir itens de compra novos
                for (ItemCompra item : compra.getItensCompra()) {
                    itemCompraDAO.inserir(item);
                }
            }
            
            return resultado == 1;
            
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
            // excluir itens da compra
            ItemCompraDAO itemCompraDAO = DAOFactory.getDefaultDAOFactory().getItemCompraDAO();
            for (ItemCompra item : itemCompraDAO.getLista(compra)) {
                itemCompraDAO.excluir(item);
            }
            
            // excluir registro de compra
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_EXCLUIR);
            ps.setLong(1, compra.getCodigo());
            
            int resultado = ps.executeUpdate();
            
            return resultado == 1;
            
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
            PreparedStatement ps = con.prepareStatement(SCRIPT_GET_LISTA);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                long codigoCompra = rs.getLong(1);
                Date dataCompra = rs.getDate(2);
                double valorTotal = rs.getDouble(3);
                long codigoComprador = rs.getLong(4);
                long codigoFornecedor = rs.getLong(5);
                
                FuncionarioDAO funcionarioDAO = DAOFactory.getDefaultDAOFactory().getFuncionarioDAO();
                Funcionario funcionarioBusca = new Funcionario(codigoComprador);
                Comprador comprador = (Comprador) funcionarioDAO.buscar(funcionarioBusca);
                
                FornecedorDAO fornecedorDAO = DAOFactory.getDefaultDAOFactory().getFornecedorDAO();
                Fornecedor fornecedorBusca = new Fornecedor(codigoFornecedor);
                Fornecedor fornecedor = fornecedorDAO.buscar(fornecedorBusca);
                
                Compra compra = new Compra(codigoCompra, dataCompra, comprador, fornecedor);
                
                ItemCompraDAO itemCompraDAO = DAOFactory.getDefaultDAOFactory().getItemCompraDAO();
                ArrayList<ItemCompra> listaItens = itemCompraDAO.getLista(compra);
                for (ItemCompra item : listaItens) {
                    compra.inserirItemCompra(item);
                }
                
                listaCompra.add(compra);
            }
            
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
