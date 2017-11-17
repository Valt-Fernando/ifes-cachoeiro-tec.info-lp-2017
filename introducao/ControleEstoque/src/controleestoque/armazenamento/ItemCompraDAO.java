/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Compra;
import controleestoque.entidades.ItemCompra;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface ItemCompraDAO {
    
    public ItemCompra buscar(ItemCompra itemCompra);
    
    public boolean inserir(ItemCompra itemCompra);
    
    public boolean alterar(ItemCompra itemCompra);
    
    public boolean excluir(ItemCompra itemCompra);
    
    public ArrayList<ItemCompra> getLista(Compra compra);
    
}
