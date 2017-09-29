/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import controleestoque.armazenamento.ArmazenamentoFuncionario;
import controleestoque.entidades.Funcionario;
import controleestoque.entidades.Comprador;
import controleestoque.entidades.Vendedor;
import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class CadastroFuncionario {

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
            System.out.println("\n\nOpções do cadastro de funcionários:");
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
        System.out.println("\nInserir novo registro de funcionário.\n");
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine(); // <------------------- para consumir a quebra-de-linha!
        System.out.print(" - Nome....: ");
        String nome = input.nextLine();
        System.out.print(" - CPF.....: ");
        long cpf = input.nextLong();
        input.nextLine();
        System.out.print(" - Endereço: ");
        String endereco = input.nextLine();
        System.out.print(" - Telefone: ");
        String telefone = input.nextLine();
        System.out.print(" - Email...: ");
        String email = input.nextLine();
        
        char cargo;
        Funcionario novoFuncionario = null;
        do {
            System.out.print(" - Cargo...: (C=Comprador/V=Vendedor) ");
            cargo = input.nextLine().charAt(0);

            switch (cargo) {
                case 'C':
                    novoFuncionario = new Comprador(codigo, nome, cpf, endereco, telefone, email);
                    break;
                case 'V':
                    novoFuncionario = new Vendedor(codigo, nome, cpf, endereco, telefone, email);
                    break;
                default:
                    System.out.println("VALOR INVÁLIDO! DIGITE C PARA COMPRADOR OU F PARA VENDEDOR.");
            }
        } while (cargo != 'C' && cargo != 'V');
        
        ArmazenamentoFuncionario.inserir(novoFuncionario);
    }
    
    private void listar() {
        System.out.println("\nListagem de funcionários registrados.\n");
        System.out.println("+--------+--------------------------------+-------------+-----------------+-----------+");
        System.out.println("| Código | Nome                           | CPF         | Telefone        | Cargo     |");
        System.out.println("+--------+--------------------------------+-------------+-----------------+-----------+");
        for (Funcionario f : ArmazenamentoFuncionario.getLista()) {
            System.out.printf("| %6d | %-30s | %11d | %15s | %-9s |\n", f.getCodigo(), f.getNome().substring(0, 30), f.getCpf(),
                    f.getTelefone(), (f instanceof Vendedor ? "Vendedor" : "Comprador"));
        }
        System.out.println("+--------+--------------------------------+-------------+-----------------+-----------+");
    }
    
    private void alterar() {
        System.out.println("\nAlterar registro de funcionário.\n");
        
        // obter o código do produto a alterar
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // procurar o produto para alterar na lista de funcionários
        Funcionario f = new Funcionario(codigo);
        Funcionario funcionarioParaAlterar = ArmazenamentoFuncionario.buscar(f);

        // caso não encontre, exibir mensagem de erro ao usuário
        if (funcionarioParaAlterar == null) {
            System.out.println("NÃO HÁ FUNCIONÁRIO CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        // exibir nome
        System.out.println("\n - Nome: " + funcionarioParaAlterar.getNome());
        // perguntar se quer alterar o nome
        System.out.print(" --> Alterar o nome? (s=sim/n=não) ");
        char opcaoNome = input.nextLine().charAt(0);
        
        String nome = funcionarioParaAlterar.getNome();
        if (opcaoNome == 's') {
            System.out.print(" - Novo nome: ");
            nome = input.nextLine();
        }
                
        // exibir cpf
        System.out.println("\n - CPF: " + funcionarioParaAlterar.getCpf());
        // perguntar se quer alterar o cpf
        System.out.print(" --> Alterar o CPF? (s=sim/n=não) ");
        char opcaoCpf = input.nextLine().charAt(0);
        
        long cpf = funcionarioParaAlterar.getCpf();
        if (opcaoCpf == 's') {
            System.out.print(" - Novo CPF: ");
            cpf = input.nextLong();
            input.nextLine();
        }
                
        // exibir endereço
        System.out.println("\n - Endereço: " + funcionarioParaAlterar.getEndereco());
        // perguntar se quer alterar o endereço
        System.out.print(" --> Alterar o endereço? (s=sim/n=não) ");
        char opcaoEndereco = input.nextLine().charAt(0);
        
        String endereco = funcionarioParaAlterar.getEndereco();
        if (opcaoEndereco == 's') {
            System.out.print(" - Novo endereço: ");
            endereco = input.nextLine();
        }
                
        // exibir telefone
        System.out.println("\n - Telefone: " + funcionarioParaAlterar.getTelefone());
        // perguntar se quer alterar o telefone
        System.out.print(" --> Alterar o telefone? (s=sim/n=não) ");
        char opcaoTelefone = input.nextLine().charAt(0);
        
        String telefone = funcionarioParaAlterar.getTelefone();
        if (opcaoTelefone == 's') {
            System.out.print(" - Novo telefone: ");
            telefone = input.nextLine();
        }
                
        // exibir email
        System.out.println("\n - Email: " + funcionarioParaAlterar.getEmail());
        // perguntar se quer alterar o email
        System.out.print(" --> Alterar o email? (s=sim/n=não) ");
        char opcaoEmail = input.nextLine().charAt(0);
        
        String email = funcionarioParaAlterar.getEmail();
        if (opcaoEmail == 's') {
            System.out.print(" - Novo email: ");
            email = input.nextLine();
        }
                
        // confirmação final!!!
        System.out.println("\nConfirma alteração do funcionário?");
        System.out.printf(" - Código..: %d\n", funcionarioParaAlterar.getCodigo());
        System.out.printf(" - Nome....: %s\n", nome);
        System.out.printf(" - CPF.....: %d\n", cpf);
        System.out.printf(" - Endereço: %s\n", endereco);
        System.out.printf(" - Telefone: %s\n", telefone);
        System.out.printf(" - Email...: %s\n", email);
        System.out.printf(" - Cargo...: %s\n", 
                (funcionarioParaAlterar instanceof Comprador ? "Comprador" : "Vendedor"));
        System.out.print(" --> (s=sim/n=não) ");
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            Funcionario funcionarioAlterado = null;
            if (funcionarioParaAlterar instanceof Vendedor) {
                funcionarioAlterado = new Vendedor(codigo, nome, cpf, endereco, telefone, email);
            } else {
                funcionarioAlterado = new Comprador(codigo, nome, cpf, endereco, telefone, email);
            }
            ArmazenamentoFuncionario.alterar(funcionarioAlterado);
        }
    }
    
    private void excluir() {
        System.out.println("\nExcluir registro de funcionário.\n");
        
        // obter o código do funcionário a excluir
        System.out.print(" - Código do funcionário a excluir: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // buscar dados do funcionário para confirmação de exclusão
        Funcionario parametroBusca = new Funcionario(codigo);
        Funcionario funcionarioExcluir = ArmazenamentoFuncionario.buscar(parametroBusca);
        
        if (funcionarioExcluir == null) {
            System.out.println("NÃO HÁ FUNCIONÁRIO CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        System.out.println(" - Nome....: " + funcionarioExcluir.getNome());
        System.out.println(" - CPF.....: " + funcionarioExcluir.getCpf());
        System.out.println(" - Endereço: " + funcionarioExcluir.getEndereco());
        System.out.println(" - Telefone: " + funcionarioExcluir.getTelefone());
        System.out.println(" - Email...: " + funcionarioExcluir.getEmail());
        System.out.println(" - Cargo...: " + 
                (funcionarioExcluir instanceof Comprador ? "Comprador" : "Vendedor"));
        System.out.print("\n  --> Confirma exclusão? (s=sim/n=não) ");
        
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            ArmazenamentoFuncionario.excluir(funcionarioExcluir);
        }
    }
}
