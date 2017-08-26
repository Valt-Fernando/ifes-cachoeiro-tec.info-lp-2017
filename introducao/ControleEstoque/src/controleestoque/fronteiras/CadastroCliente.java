/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import controleestoque.armazenamento.ArmazenamentoCliente;
import controleestoque.entidades.Cliente;
import controleestoque.entidades.ClientePessoaFisica;
import controleestoque.entidades.ClientePessoaJuridica;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class CadastroCliente {

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
            System.out.println("\n\nOpções do cadastro de clientes:");
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
        System.out.println("\nInserir novo registro de cliente.\n");
        
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine(); // <------------------- para consumir a quebra-de-linha!
        
        // campos para cliente pessoa física
        String nome = "";
        Date dataNascimento = Calendar.getInstance().getTime(); // <-- hoje
        char sexo = '.';
        long cpf = 0;
        
        // campos para cliente pessoa jurídica
        String nomeFantasia = "";
        String razaoSocial = "";
        long cnpj = 0;
        long inscricaoEstadual = 0;
        
        // obtém os dados específicos
        char tipoPessoa;
        boolean tipoPessoaValido;
        do {
            System.out.print(" - Pessoa física (F) ou jurídica (J)? ");
            tipoPessoa = input.nextLine().toUpperCase().charAt(0);

            tipoPessoaValido = true;
            switch (tipoPessoa) {
                case 'F':
                    System.out.print(" - Nome: ");
                    nome = input.nextLine();

                    boolean dataValida;
                    do {
                        System.out.print(" - Data de nascimento (dd/mm/aaaa): ");
                        String data = input.nextLine();
                        try {
                            dataNascimento = DateFormat.getDateInstance().parse(data);
                            dataValida = true;
                        } catch (ParseException e) {
                            System.out.println("VOCÊ DIGITOU UMA DATA INVÁLIDA!");
                            dataValida = false;
                        }
                    } while (!dataValida);

                    System.out.print(" - Sexo (F=feminino;M=masculino): ");
                    sexo = input.nextLine().toUpperCase().charAt(0);

                    System.out.print(" - CPF (somente os números): ");
                    cpf = input.nextLong();
                    input.nextLine();

                    break;
                case 'J':
                    System.out.print(" - Nome fantasia: ");
                    nomeFantasia = input.nextLine();

                    System.out.print(" - Razão social: ");
                    razaoSocial = input.nextLine();

                    System.out.print(" - CNPJ (somente os números): ");
                    cnpj = input.nextLong();
                    input.nextLine();

                    System.out.print(" - Inscrição estadual (somente os números): ");
                    inscricaoEstadual = input.nextLong();
                    input.nextLine();

                    break;

                default:
                    tipoPessoaValido = false;
                    System.out.println("OPÇÃO DE TIPO DE PESSOA INVÁLIDA!");
            }
        } while (!tipoPessoaValido);
        
        System.out.print(" - Endereço: ");
        String endereco = input.nextLine();
        
        System.out.print(" - Telefone: ");
        String telefone = input.nextLine();
        
        System.out.print(" - Email: ");
        String email = input.nextLine();

        Cliente novoCliente = null;
        switch (tipoPessoa) {
            case 'F':
                novoCliente = new ClientePessoaFisica(codigo, endereco, 
                        telefone, email, cpf, sexo, nome, dataNascimento);
                break;
            case 'J':
                novoCliente = new ClientePessoaJuridica(codigo, endereco, 
                        telefone, email, cnpj, inscricaoEstadual, nomeFantasia, 
                        razaoSocial);
                break;
        }
        if (novoCliente != null) {
            ArmazenamentoCliente.inserir(novoCliente);
        }
    }
    
    private void listar() {
        System.out.println("\nListagem de clientes registrados.\n");
        System.out.println("+--------+--------------------------------+-----------------+--------------------+-----------------+");
        System.out.println("| Código | Nome/Nome fantasia             | Física/Jurídica | CPF/CNPJ           | Telefone        |");
        System.out.println("+--------+--------------------------------+-----------------+--------------------+-----------------+");
        for (Cliente c : ArmazenamentoCliente.getLista()) {
            if (c instanceof ClientePessoaFisica) {
                ClientePessoaFisica cPF = (ClientePessoaFisica) c;
                System.out.printf("| %6d | %-30s | %-15s | %18s | %15s |\n", cPF.getCodigo(), cPF.getNome(), "F", cPF.getCpf(), cPF.getTelefone());
            }
            else if (c instanceof ClientePessoaJuridica) {
                System.out.printf("| %6d | %-30s | %10.2f |\n", c.getCodigo(), c.getNome(), c.getPreco());
            }
        }
        System.out.println("+--------+--------------------------------+-----------------+--------------------+-----------------+");
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
