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
    
    protected void listar() {
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
        System.out.println("\nAlterar registro de item de compra.\n");
        
        // obter o código do item a alterar
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // procurar o item de compra para alterar na lista de itens de compra
        ItemCompra i = new ItemCompra(codigo);
        ItemCompra itemParaAlterar = ArmazenamentoItemCompra.buscar(i);

        // caso não encontre, exibir mensagem de erro ao usuário
        if (itemParaAlterar == null) {
            System.out.println("NÃO HÁ ITEM CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        // exibir produto
        System.out.printf("\n - Produto: %d - %s\n",
                itemParaAlterar.getProduto().getCodigo(),
                itemParaAlterar.getProduto().getNome());
        // perguntar se quer alterar o produto
        System.out.print(" --> Alterar o produto? (s=sim/n=não) ");
        char opcaoProduto = input.nextLine().charAt(0);
        
        Produto produto = itemParaAlterar.getProduto();
        if (opcaoProduto == 's') {
            boolean produtoExistente = false;
            do {
                System.out.print(" - Novo produto (código): ");
                long codigoProduto = input.nextLong();
                Produto novoProduto = ArmazenamentoProduto.buscar(new Produto(codigoProduto));
                if (novoProduto != null) {
                    produtoExistente = true;
                    produto = novoProduto;
                } else {
                    System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
                }
            } while (!produtoExistente);
        }
        
        // exibir preço de compra
        System.out.printf("\n - Preço de compra: %.2f\n", itemParaAlterar.getPrecoCompra());
        // perguntar se quer alterar o preço de compra
        System.out.print(" --> Alterar o preço de compra? (s=sim/n=não) ");
        char opcaoPrecoCompra = input.nextLine().charAt(0);
        
        double precoCompra = itemParaAlterar.getPrecoCompra();
        if (opcaoPrecoCompra == 's') {
            System.out.print(" - Novo preço de compra: ");
            precoCompra = input.nextDouble();
            input.nextLine();
        }
        
        // exibir quantidade
        System.out.printf("\n - Quantidade: %d\n", itemParaAlterar.getQuantidade());
        // perguntar se quer alterar a quantidade
        System.out.print(" --> Alterar a quantidade? (s=sim/n=não) ");
        char opcaoQuantidade = input.nextLine().charAt(0);
        
        int quantidade = itemParaAlterar.getQuantidade();
        if (opcaoQuantidade == 's') {
            System.out.print(" - Nova quantidade: ");
            quantidade = input.nextInt();
            input.nextLine();
        }
        
        // confirmação final!!!
        System.out.println("\nConfirma alteração do item de compra?");
        System.out.printf(" - Código.........: %d\n", itemParaAlterar.getCodigo());
        System.out.printf(" - Produto........: %d - %s\n", produto.getCodigo(), 
                produto.getNome());
        System.out.printf(" - Preço de compra: %.2f\n", precoCompra);
        System.out.printf(" - Quantidade.....: %d\n", quantidade);
        
        System.out.print(" --> (s=sim/n=não) ");
        char opcao = input.nextLine().charAt(0);
        
        if (opcao == 's') {
            ItemCompra itemAlterado = new ItemCompra(codigo, compraReferencia, 
                    produto, precoCompra, quantidade);
            
            ArmazenamentoItemCompra.alterar(itemAlterado);
        }
    }
    
    private void excluir() {
        System.out.println("\nExcluir registro de item de compra.\n");
        
        // obter o código do item de compra a excluir
        System.out.print(" - Código do item a excluir: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // buscar dados do item para confirmação de exclusão
        ItemCompra parametroBusca = new ItemCompra(codigo);
        ItemCompra itemExcluir = ArmazenamentoItemCompra.buscar(parametroBusca);
        
        if (itemExcluir == null) {
            System.out.println("NÃO HÁ ITEM CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        System.out.printf(" - Código.........: %d\n", itemExcluir.getCodigo());
        System.out.printf(" - Produto........: %d - %s\n", 
                itemExcluir.getProduto().getCodigo(), 
                itemExcluir.getProduto().getNome());
        System.out.printf(" - Preço de compra: %.2f\n", itemExcluir.getPrecoCompra());
        System.out.printf(" - Quantidade.....: %d\n", itemExcluir.getQuantidade());

        System.out.print("\n  --> Confirma exclusão? (s=sim/n=não) ");
        
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            ArmazenamentoItemCompra.excluir(itemExcluir);
        }
    }
}
