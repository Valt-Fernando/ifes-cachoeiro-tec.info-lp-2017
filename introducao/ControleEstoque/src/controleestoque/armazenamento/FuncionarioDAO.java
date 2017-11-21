/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Funcionario;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface FuncionarioDAO {
    
    public Funcionario buscar(Funcionario funcionario);
    
    public boolean inserir(Funcionario funcionario);
    
    public boolean alterar(Funcionario funcionario);
    
    public boolean excluir(Funcionario funcionario);

    public ArrayList<Funcionario> getLista();
    
}
