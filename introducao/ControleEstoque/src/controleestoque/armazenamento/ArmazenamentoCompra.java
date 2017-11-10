/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Compra;
import controleestoque.entidades.Entidade;
import controleestoque.entidades.ItemCompra;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ArmazenamentoCompra extends Armazenamento {
    
    @Override
    public boolean alterar(Entidade e) {
        Compra c = (Compra) e;
        Compra compraAlterar = (Compra) buscar(c);
        if (compraAlterar != null) {
            compraAlterar.setComprador(c.getComprador());
            compraAlterar.setData(c.getData());
            compraAlterar.setFornecedor(c.getFornecedor());
            compraAlterar.setValorTotal(c.getValorTotal());
            ArrayList<ItemCompra> itensCompra = compraAlterar.getItensCompra();
            itensCompra.clear();
            itensCompra.addAll(c.getItensCompra());
            return true;
        }
        return false;
    }
}
