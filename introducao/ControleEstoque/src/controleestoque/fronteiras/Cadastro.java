/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public abstract class Cadastro {

    private static final int OPCAO_INSERIR = 1;
    private static final int OPCAO_LISTAR = 2;
    private static final int OPCAO_ALTERAR = 3;
    private static final int OPCAO_EXCLUIR = 4;
    private static final int OPCAO_VOLTAR_MENU_ANTERIOR = 5;

    protected Scanner input;

    public void exibirMenu() {
        input = new Scanner(System.in);

        int opcao = 0;
        while (opcao != OPCAO_VOLTAR_MENU_ANTERIOR) {
            System.out.printf("\n\n%s\n", obterTituloMenu());
            System.out.println(" 1 - Inserir");
            System.out.println(" 2 - Listar");
            System.out.println(" 3 - Alterar");
            System.out.println(" 4 - Excluir");
            System.out.printf(" 5 - %s\n", obterMensagemSairDoMenu());
            System.out.print("---> Digite o número da opção desejada e tecle ENTER: ");

            opcao = input.nextInt();
            processarOpcaoUsuario(opcao);
        }
    }
    
    protected abstract String obterTituloMenu();
    
    protected abstract String obterMensagemSairDoMenu();

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

    protected abstract void inserir();
    
    protected abstract void listar();
    
    protected abstract void alterar();
    
    protected abstract void excluir();
    
}
