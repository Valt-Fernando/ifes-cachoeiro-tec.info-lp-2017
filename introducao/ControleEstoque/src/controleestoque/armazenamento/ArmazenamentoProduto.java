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
public class ArmazenamentoProduto {

    private static ArrayList<Produto> LISTA_PRODUTO;
    
    public static ArrayList<Produto> getLista() {
        return LISTA_PRODUTO;
    }
    
    public static void iniciarLista() {
        LISTA_PRODUTO = new ArrayList<>();
    }
    
    public static void inserir(Produto p) {
        LISTA_PRODUTO.add(p);
    }
    
    public static boolean alterar(Produto p) {
        Produto produtoParaAlterar = null;
        for (Produto prod : LISTA_PRODUTO) {
            if (prod.getCodigo() == p.getCodigo()) {
                produtoParaAlterar = prod;
                break;
            }
        }
        if (produtoParaAlterar != null) {
            produtoParaAlterar.setNome(p.getNome());
            produtoParaAlterar.setPreco(p.getPreco());
            return true;
        }
        return false;
    }
    
    public static boolean excluir(Produto p) {
        Produto produtoParaExcluir = null;
        for (Produto prod : LISTA_PRODUTO) {
            if (prod.getCodigo() == p.getCodigo()) {
                produtoParaExcluir = prod;
                break;
            }
        }
        if (produtoParaExcluir != null) {
            LISTA_PRODUTO.remove(produtoParaExcluir);
            return true;
        }
        return false;
    }
    
    public static Produto buscar(Produto p) {
        Produto produtoProcurado = null;
        for (Produto prod : LISTA_PRODUTO) {
            if (prod.getCodigo() == p.getCodigo()) {
                produtoProcurado = prod;
                break;
            }
        }
        return produtoProcurado;
    }

}
