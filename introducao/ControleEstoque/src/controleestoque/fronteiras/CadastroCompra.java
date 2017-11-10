/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import controleestoque.armazenamento.ArmazenamentoCompra;
import controleestoque.armazenamento.ArmazenamentoFornecedor;
import controleestoque.armazenamento.ArmazenamentoFuncionario;
import controleestoque.armazenamento.ArmazenamentoItemCompra;
import controleestoque.armazenamento.ArmazenamentoProduto;
import controleestoque.entidades.Compra;
import controleestoque.entidades.Comprador;
import controleestoque.entidades.Fornecedor;
import controleestoque.entidades.Funcionario;
import controleestoque.entidades.ItemCompra;
import controleestoque.entidades.Produto;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class CadastroCompra {

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
            System.out.println("\n\nOpções do cadastro de compra:");
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
        System.out.println("\nInserir novo registro de compra.\n");

        // obter dados da compra
        
        // código
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine(); // <------------------- para consumir a quebra-de-linha!
        
        // data
        DateFormat df = DateFormat.getDateInstance();
        Date data = null;
        while (data == null) {
            System.out.print(" - Data: ");
            String stringData = input.nextLine();
            try {
                data = df.parse(stringData);
            } catch (ParseException e) {
                System.out.println("DATA INVÁLIDA! DIGITE UMA DATA VÁLIDA.");
            }
        }
        
        // comprador
        Comprador comprador = null;
        while (comprador == null) {
            System.out.print(" - Comprador (código): ");
            long codigoComprador = input.nextLong();
            input.nextLine();
            Funcionario f = ArmazenamentoFuncionario.buscar(
                    new Funcionario(codigoComprador));
            if (f instanceof Comprador) {
                comprador = (Comprador) f;
            }
            if (comprador == null) {
                System.out.println("O CÓDIGO DIGITADO NÃO É DE UM COMPRADOR CADASTRADO.");
            }
        }
        
        // fornecedor
        Fornecedor fornecedor = null;
        while (fornecedor == null) {
            System.out.print(" - Fornecedor (código): ");
            long codigoFornecedor = input.nextLong();
            input.nextLine();
            fornecedor = ArmazenamentoFornecedor.buscar(new Fornecedor(codigoFornecedor));
            if (fornecedor == null) {
                System.out.println("O CÓDIGO DIGITADO NÃO É DE UM FORNECEDOR CADASTRADO.");
            }
        }

        // já com os dados da compra, passar para o cadastro de itens da compra
        Compra compra = new Compra(codigo, data, comprador, fornecedor);
        CadastroItemCompra cadastroItemCompra = new CadastroItemCompra(compra);
        cadastroItemCompra.exibirMenu();
        
        // a lista com os itens da compra está no ArmazenamentoItemCompra
        for (ItemCompra i : ArmazenamentoItemCompra.getLista()) {
            compra.inserirItemCompra(i);
        }
        
        // confirmação de cadastro:
        System.out.println("\nConfirmação de dados de compra:");
        System.out.printf(" - Código.....: %d\n", compra.getCodigo());
        System.out.printf(" - Data.......: %s\n", df.format(compra.getData()));
        System.out.printf(" - Comprador..: %s\n", compra.getComprador().getNome());
        System.out.printf(" - Fornecedor.: %s\n", compra.getFornecedor().getNomeFantasia());
        System.out.printf(" - Valor total: %s\n", compra.getValorTotal());
        cadastroItemCompra.listar();
        System.out.print(" --> Confirmar? (s=sim/n=não) ");
        char opcao = input.nextLine().charAt(0);
        
        if (opcao == 's') {
            ArmazenamentoCompra.inserir(compra);
        }
    }

    private void listar() {
        System.out.println("\nListagem de compras registradas.\n");
        System.out.println("+--------+------------+-------------+--------------------------------+--------------------------------+");
        System.out.println("| Código | Data       | Valor total | Comprador                      | Fornecedor                     |");
        System.out.println("+--------+------------+-------------+--------------------------------+--------------------------------+");
        DateFormat df = DateFormat.getDateInstance();
        for (Compra c : ArmazenamentoCompra.getLista()) {
            String nomeComprador = c.getComprador().getNome();
            nomeComprador = nomeComprador.length() > 30 ? nomeComprador.substring(0, 30) : nomeComprador;
            String nomeFornecedor = c.getFornecedor().getNomeFantasia();
            nomeFornecedor = nomeFornecedor.length() > 30 ? nomeFornecedor.substring(0, 30) : nomeFornecedor;
            System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", 
                    c.getCodigo(), df.format(c.getData()), c.getValorTotal(), 
                    nomeComprador, nomeFornecedor);
        }
        System.out.println("+--------+------------+-------------+--------------------------------+--------------------------------+");
    }

    private void alterar() {
        System.out.println("\nAlterar registro de compra.\n");
        
        // obter o código da compra a alterar
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // procurar a compra para alterar na lista de compras
        Compra c = new Compra(codigo);
        Compra compraParaAlterar = ArmazenamentoCompra.buscar(c);

        // caso não encontre, exibir mensagem de erro ao usuário
        if (compraParaAlterar == null) {
            System.out.println("NÃO HÁ COMPRA CADASTRADA COM O CÓDIGO INFORMADO.");
            return;
        }
        
        // alteração do campo data:
        DateFormat df = DateFormat.getDateInstance();
        String dataFormatada = df.format(compraParaAlterar.getData());
        System.out.println("\n - Data: " + dataFormatada);
        // perguntar se quer alterar a data
        System.out.print(" --> Alterar a data? (s=sim/n=não) ");
        char opcaoData = input.nextLine().charAt(0);

        Date data = compraParaAlterar.getData();
        if (opcaoData == 's') {
            boolean dataValida;
            do {
                System.out.print(" - Nova data (dd/mm/aaaa): ");
                String strData = input.nextLine();
                try {
                    data = DateFormat.getDateInstance().parse(strData);
                    dataValida = true;
                } catch (ParseException e) {
                    System.out.println("VOCÊ DIGITOU UMA DATA INVÁLIDA!");
                    dataValida = false;
                }
            } while (!dataValida);
        }
        
        // alteração do campo comprador:
        System.out.printf("\n - Comprador: %d - %s\n",
                compraParaAlterar.getComprador().getCodigo(),
                compraParaAlterar.getComprador().getNome());
        // perguntar se quer alterar o comprador
        System.out.print(" --> Alterar o comprador? (s=sim/n=não) ");
        char opcaoComprador = input.nextLine().charAt(0);
        
        Comprador comprador = compraParaAlterar.getComprador();
        if (opcaoComprador == 's') {
            boolean compradorExistente = false;
            do {
                System.out.print(" - Novo comprador (código): ");
                long codigoComprador = input.nextLong();
                Funcionario novoComprador = ArmazenamentoFuncionario.buscar(new Funcionario(codigoComprador));
                if (novoComprador != null && novoComprador instanceof Comprador) {
                    compradorExistente = true;
                    comprador = (Comprador) novoComprador;
                } else {
                    System.out.println("NÃO HÁ COMPRADOR CADASTRADO COM O CÓDIGO INFORMADO.");
                }
            } while (!compradorExistente);
        }
        
        // alteração do campo fornecedor:
        System.out.printf("\n - Fornecedor: %d - %s\n",
                compraParaAlterar.getFornecedor().getCodigo(),
                compraParaAlterar.getFornecedor().getNomeFantasia());
        // perguntar se quer alterar o fornecedor
        System.out.print(" --> Alterar o fornecedor? (s=sim/n=não) ");
        char opcaoFornecedor = input.nextLine().charAt(0);
        
        Fornecedor fornecedor = compraParaAlterar.getFornecedor();
        if (opcaoFornecedor == 's') {
            boolean fornecedorExistente = false;
            do {
                System.out.print(" - Novo fornecedor (código): ");
                long codigoFornecedor = input.nextLong();
                Fornecedor novoFornecedor = ArmazenamentoFornecedor.buscar(new Fornecedor(codigoFornecedor));
                if (novoFornecedor != null) {
                    fornecedorExistente = true;
                    fornecedor = novoFornecedor;
                } else {
                    System.out.println("NÃO HÁ FORNECEDOR CADASTRADO COM O CÓDIGO INFORMADO.");
                }
            } while (!fornecedorExistente);
        }
        
        // confirmação de alteração de dados do registro de compra:
        System.out.println("\nConfirma alteração da compra?");
        System.out.printf(" - Código....: %d\n", compraParaAlterar.getCodigo());
        System.out.printf(" - Data......: %s\n", df.format(data));
        System.out.printf(" - Comprador.: %d - %s\n", comprador.getCodigo(), 
                comprador.getNome());
        System.out.printf(" - Fornecedor: %d - %s\n", fornecedor.getCodigo(), 
                fornecedor.getNomeFantasia());
        
        System.out.print(" --> (s=sim/n=não) ");
        char opcao = input.nextLine().charAt(0);
        
        if (opcao == 's') {
            Compra compraAlterada = new Compra(codigo, data, comprador, fornecedor);
            compraAlterada.getItensCompra().addAll(compraParaAlterar.getItensCompra());
            ArmazenamentoCompra.alterar(compraAlterada);
        }
        
        // edição de itens da compra:
        System.out.print("\nDeseja editar os itens da compra? (s=sim/n=não) ");
        opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            compraParaAlterar = ArmazenamentoCompra.buscar(compraParaAlterar);
            CadastroItemCompra cadastroItemCompra = new CadastroItemCompra(compraParaAlterar);
            cadastroItemCompra.exibirMenu();
        }
        // a lista com os itens da compra está no ArmazenamentoItemCompra
        compraParaAlterar.getItensCompra().clear();
        for (ItemCompra i : ArmazenamentoItemCompra.getLista()) {
            compraParaAlterar.inserirItemCompra(i);
        }
    }

    private void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
