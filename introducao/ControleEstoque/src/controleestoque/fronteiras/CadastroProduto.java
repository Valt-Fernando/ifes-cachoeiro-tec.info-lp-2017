/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import controleestoque.ControleEstoque;
import controleestoque.entidades.Produto;
import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class CadastroProduto {

    private static final int OPCAO_INSERIR = 1;
    private static final int OPCAO_LISTAR = 2;
    private static final int OPCAO_ALTERAR = 3;
    private static final int OPCAO_EXCLUIR = 4;
    private static final int OPCAO_VOLTAR_MENU_ANTERIOR = 5;
    
    private Scanner input;
    
    public void exibirMenu() {
        input = new Scanner(System.in);
        
        int opcao = 0;
        while (opcao != OPCAO_VOLTAR_MENU_ANTERIOR) {
            System.out.println("\n\nOpções do cadastro de produtos:");
            System.out.println(" 1 - Inserir");
            System.out.println(" 2 - Listar");
            System.out.println(" 3 - Alterar");
            System.out.println(" 4 - Excluir");
            System.out.println(" 5 - Voltar ao menu anterior");
            System.out.print("---> Digite o número da opção desejada e tecle ENTER: ");
            
            opcao = input.nextInt();
            processarOpcaoUsuario(opcao);
        }
    }

    private void processarOpcaoUsuario(int opcao) {
        switch (opcao) {
            case OPCAO_INSERIR:
                inserir();
                break;
            case OPCAO_LISTAR:
                listar();
                break;
            case OPCAO_ALTERAR:
                alterar();
                break;
            case OPCAO_EXCLUIR:
                excluir();
                break;
            default:
                if (opcao != OPCAO_VOLTAR_MENU_ANTERIOR) {
                    System.out.println("VOCÊ DIGITOU UMA OPÇÃO INVÁLIDA!");
                }
        }
    }
    
    private void inserir() {
        System.out.println("\nInserir novo registro de produto.\n");
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine(); // <------------------- para consumir a quebra-de-linha!
        System.out.print(" - Nome..: ");
        String nome = input.nextLine();
        System.out.print(" - Preço.: ");
        double preco = input.nextDouble();
        
        Produto novoProduto = new Produto(codigo, nome, preco);
        ControleEstoque.LISTA_PRODUTO.add(novoProduto);
    }
    
    private void listar() {
        System.out.println("\nListagem de produtos registrados.\n");
        System.out.println("+--------+--------------------------------+------------+");
        System.out.println("| Código | Nome                           | Preço      |");
        System.out.println("+--------+--------------------------------+------------+");
        for (Produto p : ControleEstoque.LISTA_PRODUTO) {
            System.out.printf("| %6d | %-30s | %10.2f |\n", p.getCodigo(), p.getNome(), p.getPreco());
        }
        System.out.println("+--------+--------------------------------+------------+");
    }
    
    private void alterar() {
        
    }
    
    private void excluir() {
        
    }
}
