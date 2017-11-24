/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.ClienteDAO;
import controleestoque.armazenamento.DAOFactory;
import controleestoque.armazenamento.FuncionarioDAO;
import controleestoque.armazenamento.ItemVendaDAO;
import controleestoque.armazenamento.VendaDAO;
import controleestoque.entidades.Cliente;
import controleestoque.entidades.Funcionario;
import controleestoque.entidades.ItemVenda;
import controleestoque.entidades.Venda;
import controleestoque.entidades.Vendedor;
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
public class PgVendaDAO implements VendaDAO {
    
    private static final String SCRIPT_BUSCAR =
            "select codigo, datavenda, valortotal, vendedor, cliente " +
            "  from venda " +
            " where codigo = ?";
    
    private static final String SCRIPT_INSERIR =
            "insert into venda (datavenda, valortotal, vendedor, cliente) " +
            "values (?, ?, ?, ?)";
    
    private static final String SCRIPT_ALTERAR =
            "update venda " +
            "   set datavenda = ?, " +
            "       valortotal = ?, " +
            "       vendedor = ?, " +
            "       cliente = ? " +
            " where codigo = ?";
    
    private static final String SCRIPT_EXCLUIR =
            "delete from venda where codigo = ?";
    
    private static final String SCRIPT_GET_LISTA =
            "select codigo, datavenda, valortotal, vendedor, cliente " +
            "  from venda " +
            " order by datavenda desc";

    @Override
    public Venda buscar(Venda venda) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            ps.setLong(1, venda.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long codigoVenda = rs.getLong(1);
                Date dataVenda = rs.getDate(2);
                double valorTotal = rs.getDouble(3);
                long codigoVendedor = rs.getLong(4);
                long codigoCliente = rs.getLong(5);
                
                // construir objeto Vendedor
                FuncionarioDAO funcionarioDAO = 
                        DAOFactory.getDefaultDAOFactory().getFuncionarioDAO();
                Funcionario f = new Funcionario(codigoVendedor);
                Vendedor vendedor = (Vendedor) funcionarioDAO.buscar(f);
                
                // construir objeto Cliente
                ClienteDAO clienteDAO = DAOFactory.getDefaultDAOFactory().getClienteDAO();
                Cliente clienteBusca = new Cliente(codigoCliente);
                Cliente cliente = clienteDAO.buscar(clienteBusca);
                
                // obter lista de itens de venda
                ItemVendaDAO itemVendaDAO =
                        DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
                Venda parametroVenda = new Venda(codigoVenda);
                ArrayList<ItemVenda> listaItemVenda = 
                        itemVendaDAO.getLista(parametroVenda);
                
                // construir objeto Venda
                Venda vendaBuscada = new Venda(codigoVenda, dataVenda, 
                        vendedor, cliente);
                for (ItemVenda item : listaItemVenda) {
                    item.setVenda(vendaBuscada);
                    vendaBuscada.inserirItemVenda(item);
                }
                
                return vendaBuscada;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao buscar registro de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean inserir(Venda venda) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR, Statement.RETURN_GENERATED_KEYS);
            java.sql.Date dataParaInserir = new java.sql.Date(venda.getData().getTime());
            ps.setDate(1, dataParaInserir);
            ps.setDouble(2, venda.getValorTotal());
            ps.setLong(3, venda.getVendedor().getCodigo());
            ps.setLong(4, venda.getCliente().getCodigo());
            
            int resultado = ps.executeUpdate();
            
            if (resultado == 1) {
                // obtém o código gerado ao inserir registro
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                long codigo = rs.getLong(1);
                venda.setCodigo(codigo);
                
                // insere lista de itens da venda
                ItemVendaDAO itemVendaDAO = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
                for (ItemVenda item : venda.getItensVenda()) {
                    itemVendaDAO.inserir(item);
                }
            }
            
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao inserir registro de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean alterar(Venda venda) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_ALTERAR);
            java.sql.Date dataParaAlterar = new java.sql.Date(venda.getData().getTime());
            ps.setDate(1, dataParaAlterar);
            ps.setDouble(2, venda.getValorTotal());
            ps.setLong(3, venda.getVendedor().getCodigo());
            ps.setLong(4, venda.getCliente().getCodigo());
            ps.setLong(5, venda.getCodigo());
            
            int resultado = ps.executeUpdate();
            
            // alterar lista de itens
            if (resultado == 1) {
                ItemVendaDAO itemVendaDAO = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
                
                // excluir itens de compra antigos (talvez até sejam os mesmos. em outra versão, poderia ser feita uma verificação)
                ArrayList<ItemVenda> listaItensAntigos = itemVendaDAO.getLista(venda);
                for (ItemVenda item : listaItensAntigos) {
                    itemVendaDAO.excluir(item);
                }
                
                // inserir itens de venda novos
                for (ItemVenda item : venda.getItensVenda()) {
                    itemVendaDAO.inserir(item);
                }
            }
            
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao alterar registro de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean excluir(Venda venda) {
        try {
            // excluir itens da venda
            ItemVendaDAO itemVendaDAO = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
            for (ItemVenda item : itemVendaDAO.getLista(venda)) {
                itemVendaDAO.excluir(item);
            }
            
            // excluir registro de venda
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_EXCLUIR);
            ps.setLong(1, venda.getCodigo());
            
            int resultado = ps.executeUpdate();
            
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao excluir registro de venda.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private ArrayList<Venda> listaVenda;
    
    @Override
    public ArrayList<Venda> getLista() {
        // inicializa a lista, se ainda não tiver sido inicializada
        if (listaVenda == null)
            listaVenda = new ArrayList<>();
        // ou limpa a lista, se já tiver sido inicializada
        else
            listaVenda.clear();
        
        // procede a execução do SELECT no banco de dados
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_GET_LISTA);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                long codigoVenda = rs.getLong(1);
                Date dataVenda = rs.getDate(2);
                double valorTotal = rs.getDouble(3);
                long codigoVendedor = rs.getLong(4);
                long codigoCliente = rs.getLong(5);
                
                // construir objeto Vendedor
                FuncionarioDAO funcionarioDAO = 
                        DAOFactory.getDefaultDAOFactory().getFuncionarioDAO();
                Funcionario f = new Funcionario(codigoVendedor);
                Vendedor vendedor = (Vendedor) funcionarioDAO.buscar(f);
                
                // construir objeto Cliente
                ClienteDAO clienteDAO = DAOFactory.getDefaultDAOFactory().getClienteDAO();
                Cliente clienteBusca = new Cliente(codigoCliente);
                Cliente cliente = clienteDAO.buscar(clienteBusca);
                
                // obter lista de itens de venda
                ItemVendaDAO itemVendaDAO =
                        DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
                Venda parametroVenda = new Venda(codigoVenda);
                ArrayList<ItemVenda> listaItemVenda = 
                        itemVendaDAO.getLista(parametroVenda);
                
                // construir objeto Venda
                Venda venda = new Venda(codigoVenda, dataVenda, vendedor, 
                        cliente);
                for (ItemVenda item : listaItemVenda) {
                    item.setVenda(venda);
                    venda.inserirItemVenda(item);
                }
                
                listaVenda.add(venda);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao buscar registros de vendas.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaVenda;
    }
    
}
