/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import controleestoque.armazenamento.ArmazenamentoItemCompra;
import controleestoque.armazenamento.ArmazenamentoProduto;
import controleestoque.entidades.Compra;
import controleestoque.entidades.ItemCompra;
import controleestoque.entidades.Produto;
import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class CadastroItemCompra {

    private static final int OPCAO_INSERIR = 1;
    private static final int OPCAO_LISTAR = 2;
    private static final int OPCAO_ALTERAR = 3;
    private static final int OPCAO_EXCLUIR = 4;
    private static final int OPCAO_CONCLUIR_CADASTRO = 5;
    
    private Scanner input;
    
    private Compra compraReferencia;
    
    public CadastroItemCompra(Compra c) {
        ArmazenamentoItemCompra.iniciarLista();
        ArmazenamentoItemCompra.getLista().addAll(c.getItensCompra());
        compraReferencia = c;
    }
    
    public void exibirMenu() {
        input = new Scanner(System.in);
        
        int opcao = 0;
        while (opcao != OPCAO_CONCLUIR_CADASTRO) {
            System.out.println("\n\nOpções do cadastro de itens da compra:");
            System.out.println(" 1 - Inserir");
            System.out.println(" 2 - Listar");
            System.out.println(" 3 - Alterar");
            System.out.println(" 4 - Excluir");
            System.out.println(" 5 - Concluir cadastro de itens da compra");
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
                if (opcao != OPCAO_CONCLUIR_CADASTRO) {
                    System.out.println("VOCÊ DIGITOU UMA OPÇÃO INVÁLIDA!");
                }
        }
    }
    
    private void inserir() {
        System.out.println("\nInserir novo registro de item de compra.\n");
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine(); // <------------------- para consumir a quebra-de-linha!
        
        // campo produto:
        Produto produto = null;
        do {
            System.out.print(" - Produto: ");
            long codigoProduto = input.nextLong();
            input.nextLine();
            produto = ArmazenamentoProduto.buscar(new Produto(codigoProduto));
            if (produto == null) {
                System.out.println("PRODUTO NÃO CADASTRADO!");
            }
        } while (produto == null);
        
        // campo precoCompra:
        System.out.print(" - Preço de compra: ");
        double precoCompra = input.nextDouble();
        
        // campo quantidade:
        System.out.print(" - Quantidade: ");
        int quantidade = input.nextInt();
        input.nextLine();
        
        ItemCompra novoItemCompra = new ItemCompra(codigo, compraReferencia, 
                produto, precoCompra, quantidade);

        ArmazenamentoItemCompra.inserir(novoItemCompra);
    }
    
    private void listar() {
        System.out.println("\nListagem de itens de compra registrados.\n");
        System.out.println("+--------+--------------------------------+------------+------------+");
        System.out.println("| Código | Produto                        | Preço      | Quantidade |");
        System.out.println("+--------+--------------------------------+------------+------------+");
        for (ItemCompra i : ArmazenamentoItemCompra.getLista()) {
            System.out.printf("| %6d | %-30s | %10.2f | %10d |\n", i.getCodigo(), 
                    i.getProduto().getNome(), i.getPrecoCompra(), i.getQuantidade());
        }
        System.out.println("+--------+--------------------------------+------------+------------+");
    }
    
    private void alterar() {
        System.out.println("\nAlterar registro de produto.\n");
        
        // obter o código do produto a alterar
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // procurar o produto para alterar na lista de produtos
        Produto p = new Produto(codigo, "", 0);
        Produto produtoParaAlterar = ArmazenamentoProduto.buscar(p);

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
            ArmazenamentoProduto.alterar(produtoAlterado);
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
        Produto produtoExcluir = ArmazenamentoProduto.buscar(parametroBusca);
        
        if (produtoExcluir == null) {
            System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        System.out.println(" - Nome.: " + produtoExcluir.getNome());
        System.out.printf(" - Preço: %.2f\n", produtoExcluir.getPreco());
        System.out.print("\n  --> Confirma exclusão? (s=sim/n=não) ");
        
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            ArmazenamentoProduto.excluir(produtoExcluir);
        }
    }
}
