/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.FornecedorDAO;
import controleestoque.entidades.Fornecedor;
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
public class PgFornecedorDAO implements FornecedorDAO {
    
    private static final String SCRIPT_BUSCAR = 
            "select codigo, nomefantasia, razaosocial, endereco, cnpj, " +
            "       inscricaoestadual, telefone, email " +
            "  from fornecedor " +
            " where codigo = ?";

    private static final String SCRIPT_INSERIR = 
            "insert into fornecedor " +
            "       (nomefantasia, razaosocial, endereco, cnpj, " +
            "        inscricaoestadual, telefone, email) " +
            "values (?, ?, ?, ?, ?, ?, ?)";

    private static final String SCRIPT_ALTERAR = 
            "update fornecedor " +
            "   set nomefantasia = ?, " +
            "       razaosocial = ?, " +
            "       endereco = ?, " +
            "       cnpj = ?, " +
            "       inscricaoestadual = ?, " +
            "       telefone = ?, " +
            "       email = ? " +
            " where codigo = ?";

    private static final String SCRIPT_EXCLUIR = 
            "delete from fornecedor where codigo = ?";

    private static final String SCRIPT_GET_LISTA = 
            "select codigo, nomefantasia, razaosocial, endereco, cnpj, " +
            "       inscricaoestadual, telefone, email " +
            "  from fornecedor " +
            " order by nomefantasia";

    @Override
    public Fornecedor buscar(Fornecedor fornecedor) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            ps.setLong(1, fornecedor.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                long codigo = rs.getLong(1);
                String nomeFantasia = rs.getString(2);
                String razaoSocial = rs.getString(3);
                String endereco = rs.getString(4);
                long cnpj = Long.parseLong(rs.getString(5));
                long inscricaoEstadual = Long.parseLong(rs.getString(6));
                String telefone = rs.getString(7);
                String email = rs.getString(8);
                
                Fornecedor fornecedorBuscado = new Fornecedor(codigo, 
                        nomeFantasia, razaoSocial, endereco, cnpj, 
                        inscricaoEstadual, telefone, email);
                
                return fornecedorBuscado;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registro de fornecedor.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean inserir(Fornecedor fornecedor) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR);
            ps.setString(1, fornecedor.getNomeFantasia());
            ps.setString(2, fornecedor.getRazaoSocial());
            ps.setString(3, fornecedor.getEndereco());
            ps.setString(4, Long.toString(fornecedor.getCnpj()));
            ps.setString(5, Long.toString(fornecedor.getInscricaoEstadual()));
            ps.setString(6, fornecedor.getTelefone());
            ps.setString(7, fornecedor.getEmail());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao inserir registro de fornecedor.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean alterar(Fornecedor fornecedor) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_ALTERAR);
            ps.setString(1, fornecedor.getNomeFantasia());
            ps.setString(2, fornecedor.getRazaoSocial());
            ps.setString(3, fornecedor.getEndereco());
            ps.setString(4, Long.toString(fornecedor.getCnpj()));
            ps.setString(5, Long.toString(fornecedor.getInscricaoEstadual()));
            ps.setString(6, fornecedor.getTelefone());
            ps.setString(7, fornecedor.getEmail());
            ps.setLong(8, fornecedor.getCodigo());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao alterar registro de fornecedor.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean excluir(Fornecedor fornecedor) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR);
            ps.setLong(1, fornecedor.getCodigo());
            
            int resultado = ps.executeUpdate();
            return resultado == 1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao excluir registro de fornecedor.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private ArrayList<Fornecedor> listaFornecedor;
    
    @Override
    public ArrayList<Fornecedor> getLista() {
        if (listaFornecedor == null)
            listaFornecedor = new ArrayList<>();
        else
            listaFornecedor.clear();
        
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                long codigo = rs.getLong(1);
                String nomeFantasia = rs.getString(2);
                String razaoSocial = rs.getString(3);
                String endereco = rs.getString(4);
                long cnpj = Long.parseLong(rs.getString(5));
                long inscricaoEstadual = Long.parseLong(rs.getString(6));
                String telefone = rs.getString(7);
                String email = rs.getString(8);
                
                Fornecedor fornecedor = new Fornecedor(codigo, 
                        nomeFantasia, razaoSocial, endereco, cnpj, 
                        inscricaoEstadual, telefone, email);
                
                listaFornecedor.add(fornecedor);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registros de fornecedores.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaFornecedor;
    }
    
}
