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
public class ArmazenamentoCompra {
    
    private static ArrayList<Compra> LISTA_COMPRA;
    
    public static ArrayList<Compra> getLista() {
        return LISTA_COMPRA;
    }
    
    public static void iniciarLista() {
        LISTA_COMPRA = new ArrayList<>();
    }
    
    public static void inserir(Compra c) {
        LISTA_COMPRA.add(c);
    }
    
    public static boolean alterar(Compra c) {
        Compra compraAlterar = buscar(c);
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
    
    public static boolean excluir(Compra c) {
        Compra compraExcluir = buscar(c);
        if (compraExcluir != null) {
            LISTA_COMPRA.remove(compraExcluir);
            return true;
        }
        return false;
    }
    
    public static Compra buscar(Compra c) {
        for (Compra compra : LISTA_COMPRA) {
            if (compra.getCodigo() == c.getCodigo()) {
                return compra;
            }
        }
        return null;
    }
}
