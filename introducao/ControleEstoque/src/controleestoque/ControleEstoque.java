/*
 * Se for usar este código, cite o autor.
 */
package controleestoque;

import controleestoque.armazenamento.ArmazenamentoProduto;
import controleestoque.fronteiras.MenuPrincipal;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ControleEstoque {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // inicialização dos dados:
        ArmazenamentoProduto.iniciarLista();
        
        MenuPrincipal qualquerNome = new MenuPrincipal();
        qualquerNome.exibirMenu();
    }
    
}
