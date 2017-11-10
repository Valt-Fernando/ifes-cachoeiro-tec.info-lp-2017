/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import controleestoque.armazenamento.DAOFactory;
import controleestoque.armazenamento.ProdutoDAO;
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
    
    private ProdutoDAO produtoDAO;
    
    public CadastroProduto() {
        produtoDAO = DAOFactory.getDefaultDAOFactory().getProdutoDAO();
    }
    
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
            input.nextLine();
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
        System.out.print(" - Nome..: ");
        String nome = input.nextLine();
        System.out.print(" - Preço.: ");
        double preco = input.nextDouble();
        
        Produto novoProduto = new Produto(0, nome, preco);
        produtoDAO.inserir(novoProduto);
    }
    
    private void listar() {
        System.out.println("\nListagem de produtos registrados.\n");
        System.out.println("+--------+--------------------------------+------------+");
        System.out.println("| Código | Nome                           | Preço      |");
        System.out.println("+--------+--------------------------------+------------+");
        for (Produto p : produtoDAO.getLista()) {
            System.out.printf("| %6d | %-30s | %10.2f |\n", p.getCodigo(), p.getNome(), p.getPreco());
        }
        System.out.println("+--------+--------------------------------+------------+");
    }
    
    private void alterar() {
        System.out.println("\nAlterar registro de produto.\n");
        
        // obter o código do produto a alterar
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // procurar o produto para alterar na lista de produtos
        Produto p = new Produto(codigo, "", 0);
        Produto produtoParaAlterar = produtoDAO.buscar(p);

        // caso não encontre, exibir mensagem de erro ao usuário
        if (produtoParaAlterar == null) {
            System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        // exibir nome
        System.out.println("\n - Nome: " + produtoParaAlterar.getNome());
        // perguntar se quer alterar o nome
        System.out.print(" --> Alterar o nome? (s=sim/n=não) ");
        char opcaoNome = input.nextLine().charAt(0);
        
        String nome = produtoParaAlterar.getNome();
        if (opcaoNome == 's') {
            System.out.print(" - Novo nome: ");
            nome = input.nextLine();
        }
        
        // exibir preço
        System.out.printf("\n - Preço: %.2f\n", produtoParaAlterar.getPreco());
        // perguntar se quer alterar o preço
        System.out.print(" --> Alterar o preço? (s=sim/n=não) ");
        char opcaoPreco = input.nextLine().charAt(0);
        
        double preco = produtoParaAlterar.getPreco();
        if (opcaoPreco == 's') {
            System.out.print(" - Novo preço: ");
            preco = input.nextDouble();
            input.nextLine();
        }
        
        // confirmação final!!!
        System.out.println("\nConfirma alteração do produto?");
        System.out.printf(" - Código: %d\n", produtoParaAlterar.getCodigo());
        System.out.printf(" - Nome..: %s\n", nome);
        System.out.printf(" - Preço.: %.2f\n", preco);
        System.out.print(" --> (s=sim/n=não) ");
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            Produto produtoAlterado = new Produto(codigo, nome, preco);
            produtoDAO.alterar(produtoAlterado);
        }
    }
    
    private void excluir() {
        System.out.println("\nExcluir registro de produto.\n");
        
        // obter o código do produto a excluir
        System.out.print(" - Código do produto a excluir: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // buscar dados do produto para confirmação de exclusão
        Produto parametroBusca = new Produto(codigo, "", 0);
        Produto produtoExcluir = produtoDAO.buscar(parametroBusca);
        
        if (produtoExcluir == null) {
            System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        System.out.println(" - Nome.: " + produtoExcluir.getNome());
        System.out.printf(" - Preço: %.2f\n", produtoExcluir.getPreco());
        System.out.print("\n  --> Confirma exclusão? (s=sim/n=não) ");
        
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            produtoDAO.excluir(produtoExcluir);
        }
    }
}
