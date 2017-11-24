/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.FuncionarioDAO;
import controleestoque.entidades.Comprador;
import controleestoque.entidades.Funcionario;
import controleestoque.entidades.Vendedor;
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
public class PgFuncionarioDAO implements FuncionarioDAO {
    
    private static final String SCRIPT_BUSCAR =
            "select codigo, nome, cpf, endereco, telefone, email, cargo " +
            "  from funcionario " +
            " where codigo = ?";

    private static final String SCRIPT_INSERIR =
            "insert into funcionario " +
            "       (nome, cpf, endereco, telefone, email, cargo) " +
            "values (?, ?, ?, ?, ?, ?)";

    private static final String SCRIPT_ALTERAR =
            "update funcionario " +
            "   set nome = ?, " +
            "       cpf = ?, " +
            "       endereco = ?, " +
            "       telefone = ?, " +
            "       email = ?, " +
            "       cargo = ? " +
            " where codigo = ?";

    private static final String SCRIPT_EXCLUIR =
            "delete from funcionario where codigo = ?";

    private static final String SCRIPT_GET_LISTA =
            "select codigo, nome, cpf, endereco, telefone, email, cargo " +
            "  from funcionario " +
            " order by nome";

    @Override
    public Funcionario buscar(Funcionario funcionario) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            ps.setLong(1, funcionario.getCodigo());
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                long codigo = rs.getLong(1);
                String nome = rs.getString(2);
                long cpf = Long.parseLong(rs.getString(3));
                String endereco = rs.getString(4);
                String telefone = rs.getString(5);
                String email = rs.getString(6);
                char cargo = rs.getString(7).charAt(0);
                
                Funcionario funcionarioBuscado = null;
                switch (cargo) {
                    case 'V': // vendedor
                        funcionarioBuscado = new Vendedor(codigo, nome, cpf, endereco, telefone, email);
                        break;
                    case 'C': // comprador
                        funcionarioBuscado = new Comprador(codigo, nome, cpf, endereco, telefone, email);
                        break;
                }
                
                return funcionarioBuscado;
            }
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registro de funcionário.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean inserir(Funcionario funcionario) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR);
            ps.setString(1, funcionario.getNome());
            ps.setString(2, Long.toString(funcionario.getCpf()));
            ps.setString(3, funcionario.getEndereco());
            ps.setString(4, funcionario.getTelefone());
            ps.setString(5, funcionario.getEmail());
            String cargo = funcionario instanceof Vendedor ? "V" : "C";
            ps.setString(6, cargo);
            
            int resultado = ps.executeUpdate();
            
            return resultado == 1;
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao inserir registro de funcionário.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean alterar(Funcionario funcionario) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_ALTERAR);
            ps.setString(1, funcionario.getNome());
            ps.setString(2, Long.toString(funcionario.getCpf()));
            ps.setString(3, funcionario.getEndereco());
            ps.setString(4, funcionario.getTelefone());
            ps.setString(5, funcionario.getEmail());
            String cargo = funcionario instanceof Vendedor ? "V" : "C";
            ps.setString(6, cargo);
            ps.setLong(7, funcionario.getCodigo());
            
            int resultado = ps.executeUpdate();
            
            return resultado == 1;
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao alterar registro de funcionário.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean excluir(Funcionario funcionario) {
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_EXCLUIR);
            ps.setLong(1, funcionario.getCodigo());
            
            int resultado = ps.executeUpdate();
            
            return resultado == 1;
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao excluir registro de funcionário.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private ArrayList<Funcionario> listaFuncionario;
    
    @Override
    public ArrayList<Funcionario> getLista() {
        if (listaFuncionario == null)
            listaFuncionario = new ArrayList<>();
        else
            listaFuncionario.clear();
        
        try {
            Connection con = PostgreSqlDAOFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_GET_LISTA);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                long codigo = rs.getLong(1);
                String nome = rs.getString(2);
                long cpf = Long.parseLong(rs.getString(3));
                String endereco = rs.getString(4);
                String telefone = rs.getString(5);
                String email = rs.getString(6);
                char cargo = rs.getString(7).charAt(0);
                
                Funcionario funcionario = null;
                switch (cargo) {
                    case 'V': // vendedor
                        funcionario = new Vendedor(codigo, nome, cpf, endereco, telefone, email);
                        break;
                    case 'C': // comprador
                        funcionario = new Comprador(codigo, nome, cpf, endereco, telefone, email);
                        break;
                }
                
                listaFuncionario.add(funcionario);
            }
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao buscar registros de funcionários.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaFuncionario;
    }
    
}
