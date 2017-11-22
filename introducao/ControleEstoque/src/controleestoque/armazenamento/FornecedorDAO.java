/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Fornecedor;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface FornecedorDAO {
    
    public Fornecedor buscar(Fornecedor fornecedor);
    
    public boolean inserir(Fornecedor fornecedor);
    
    public boolean alterar(Fornecedor fornecedor);
    
    public boolean excluir(Fornecedor fornecedor);
    
    public ArrayList<Fornecedor> getLista();

}
