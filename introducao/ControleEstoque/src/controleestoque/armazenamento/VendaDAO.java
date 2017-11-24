/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Venda;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public interface VendaDAO {
    
    public Venda buscar(Venda venda);
    
    public boolean inserir(Venda venda);
    
    public boolean alterar(Venda venda);
    
    public boolean excluir(Venda venda);
    
    public ArrayList<Venda> getLista();
    
}
