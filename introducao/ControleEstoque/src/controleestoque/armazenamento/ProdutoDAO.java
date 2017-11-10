/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Produto;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface ProdutoDAO {
    
    public Produto buscar(Produto produto);
    
    public boolean inserir(Produto produto);
    
    public boolean alterar(Produto produto);
    
    public boolean excluir(Produto produto);
    
    public ArrayList<Produto> getLista();
    
}
