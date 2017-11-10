/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Cliente;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface ClienteDAO {
    
    public Cliente buscar(Cliente cliente);
    
    public boolean inserir(Cliente cliente);
    
    public boolean alterar(Cliente cliente);
    
    public boolean excluir(Cliente cliente);
    
    public ArrayList<Cliente> getLista();
    
}
