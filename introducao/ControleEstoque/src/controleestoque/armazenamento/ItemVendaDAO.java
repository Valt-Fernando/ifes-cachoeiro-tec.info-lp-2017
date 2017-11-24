/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.ItemVenda;
import controleestoque.entidades.Venda;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface ItemVendaDAO {
    
    public ItemVenda buscar(ItemVenda itemVenda);
    
    public boolean inserir(ItemVenda itemVenda);
    
    public boolean alterar(ItemVenda itemVenda);
    
    public boolean excluir(ItemVenda itemVenda);
    
    public ArrayList<ItemVenda> getLista(Venda venda);
    
}
