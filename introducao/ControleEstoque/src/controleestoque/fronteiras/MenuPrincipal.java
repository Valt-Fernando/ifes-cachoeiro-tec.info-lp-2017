/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class MenuPrincipal {
    private static final int OPCAO_CADASTRO_CLIENTE = 1;
    private static final int OPCAO_CADASTRO_FORNECEDOR = 2;
    private static final int OPCAO_CADASTRO_PRODUTO = 3;
    private static final int OPCAO_CADASTRO_FUNCIONARIO = 4;
    private static final int OPCAO_CADASTRO_COMPRA = 5;
    private static final int OPCAO_CADASTRO_VENDA = 6;
    private static final int OPCAO_TERMINAR_PROGRAMA = 7;
    
    public void exibirMenu() {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        while (opcao != OPCAO_TERMINAR_PROGRAMA) {
            System.out.println("\n\nOpções:");
            System.out.println(" 1 - Cliente");
            System.out.println(" 2 - Fornecedor");
            System.out.println(" 3 - Produto");
            System.out.println(" 4 - Funcionário");
            System.out.println(" 5 - Compra");
            System.out.println(" 6 - Venda");
            System.out.println(" 7 - Terminar programa");
            System.out.print("---> Digite o número da opção desejada e tecle ENTER: ");
            
            opcao = input.nextInt();
            processarOpcaoUsuario(opcao);
        }
        System.out.println("\nPrograma encerrado.\n");
    }

    private void processarOpcaoUsuario(int opcao) {
        switch (opcao) {
            case OPCAO_CADASTRO_CLIENTE:
                break;
            case OPCAO_CADASTRO_FORNECEDOR:
                CadastroFornecedor cadastroFornecedor = new CadastroFornecedor();
                cadastroFornecedor.exibirMenu();
                break;
            case OPCAO_CADASTRO_PRODUTO:
                CadastroProduto cadastroProduto = new CadastroProduto();
                cadastroProduto.exibirMenu();
                break;
            case OPCAO_CADASTRO_FUNCIONARIO:
                break;
            case OPCAO_CADASTRO_COMPRA:
                break;
            case OPCAO_CADASTRO_VENDA:
                break;
            default:
                if (opcao != OPCAO_TERMINAR_PROGRAMA) {
                    System.out.println("\nVOCÊ DIGITOU UMA OPÇÃO INVÁLIDA!");
                }
        }
    }
}
