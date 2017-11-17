/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Compra;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface CompraDAO {
    
    public Compra buscar(Compra compra);
    
    public boolean inserir(Compra compra);
    
    public boolean alterar(Compra compra);
    
    public boolean excluir(Compra compra);
    
    public ArrayList<Compra> getLista();
    
}
