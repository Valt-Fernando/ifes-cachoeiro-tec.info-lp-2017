/*
 * Se for usar este código, cite o autor.
 */
package controleestoque;

import controleestoque.entidades.Produto;
import controleestoque.fronteiras.MenuPrincipal;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ControleEstoque {
    
    public static ArrayList<Produto> LISTA_PRODUTO;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // inicialização dos dados:
        LISTA_PRODUTO = new ArrayList<>();
        
        MenuPrincipal qualquerNome = new MenuPrincipal();
        qualquerNome.exibirMenu();
    }
    
}
