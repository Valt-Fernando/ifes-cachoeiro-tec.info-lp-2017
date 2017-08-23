/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras;

import controleestoque.armazenamento.ArmazenamentoFornecedor;
import controleestoque.entidades.Fornecedor;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class CadastroFornecedor {

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
            System.out.println("\n\nOpções do cadastro de fornecedores:");
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
        System.out.println("\nInserir novo registro de fornecedor.\n");
        System.out.print(" - Código............: ");
        long codigo = input.nextLong();
        input.nextLine(); // <------------------- para consumir a quebra-de-linha!
        System.out.print(" - Nome fantasia.....: ");
        String nomeFantasia = input.nextLine();
        System.out.print(" - Razão social......: ");
        String razaoSocial = input.nextLine();
        System.out.print(" - Endereço..........: ");
        String endereco = input.nextLine();
        System.out.print(" - CNPJ..............: ");
        long cnpj = input.nextLong();
        System.out.print(" - Inscrição estadual: ");
        long inscricaoEstadual = input.nextLong();
        input.nextLine(); // <------------------- para consumir a quebra-de-linha!
        System.out.print(" - Telefone..........: ");
        String telefone = input.nextLine();
        System.out.print(" - Email.............: ");
        String email = input.nextLine();
        
        Fornecedor novoFornecedor = new Fornecedor(codigo, nomeFantasia, 
                razaoSocial, endereco, cnpj, inscricaoEstadual, telefone, email);
        ArmazenamentoFornecedor.inserir(novoFornecedor);
    }
    
    private void listar() {
        System.out.println("\nListagem de fornecedores registrados.\n");
        System.out.println("+--------+--------------------------------+----------------+-----------+-------------------+");
        System.out.println("| Código | Nome Fantasia                  | CNPJ           | Insc.Est. | Telefone          |");
        System.out.println("+--------+--------------------------------+----------------+-----------+-------------------+");
        for (Fornecedor f : ArmazenamentoFornecedor.getLista()) {
            System.out.printf("| %6d | %-30s | %14d | %9d | %17s |\n", f.getCodigo(), 
                    f.getNomeFantasia(), f.getCnpj(), f.getInscricaoEstadual(),
                    f.getTelefone());
        }
        System.out.println("+--------+--------------------------------+----------------+-----------+-------------------+");
    }
    
    // 11223562000200 -> 11.223.562/0002-00
    private String formatarCnpj(long cnpj) {
        String cnpjFormatado = "";
        
        // formatar o cnpj
        
        return cnpjFormatado;
    }
    
    private void alterar() {
        System.out.println("\nAlterar registro de fornecedor.\n");
        
        // obter o código do fornecedor a alterar
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // procurar o fornecedor para alterar na lista de fornecedores
        Fornecedor f = new Fornecedor(codigo);
        Fornecedor fornecedorParaAlterar = ArmazenamentoFornecedor.buscar(f);

        // caso não encontre, exibir mensagem de erro ao usuário
        if (fornecedorParaAlterar == null) {
            System.out.println("NÃO HÁ FORNECEDOR CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        // exibir nome fantasia
        System.out.println("\n - Nome fantasia: " + 
                fornecedorParaAlterar.getNomeFantasia());
        // perguntar se quer alterar o nome fantasia
        System.out.print(" --> Alterar o nome fantasia? (s=sim/n=não) ");
        char opcaoNomeFantasia = input.nextLine().charAt(0);
        
        String nomeFantasia = fornecedorParaAlterar.getNomeFantasia();
        if (opcaoNomeFantasia == 's') {
            System.out.print(" - Novo nome fantasia: ");
            nomeFantasia = input.nextLine();
        }
        
        // exibir razão social
        System.out.println("\n - Razão social: " + 
                fornecedorParaAlterar.getRazaoSocial());
        // perguntar se quer alterar a razão social
        System.out.print(" --> Alterar a razão social? (s=sim/n=não) ");
        char opcaoRazaoSocial = input.nextLine().charAt(0);
        
        String razaoSocial = fornecedorParaAlterar.getRazaoSocial();
        if (opcaoRazaoSocial == 's') {
            System.out.print(" - Nova razão social: ");
            razaoSocial = input.nextLine();
        }
        
        // exibir endereço
        System.out.println("\n - Endereço: " + 
                fornecedorParaAlterar.getEndereco());
        // perguntar se quer alterar o endereço
        System.out.print(" --> Alterar o endereço? (s=sim/n=não) ");
        char opcaoEndereco = input.nextLine().charAt(0);
        
        String endereco = fornecedorParaAlterar.getEndereco();
        if (opcaoEndereco == 's') {
            System.out.print(" - Novo endereço: ");
            endereco = input.nextLine();
        }
        
        // exibir CNPJ
        String cnpjFormatado = formatarCnpj(fornecedorParaAlterar.getCnpj());
        System.out.println("\n - CNPJ: " + cnpjFormatado);
        
        // perguntar se quer alterar o CNPJ
        System.out.print(" --> Alterar o CNPJ? (s=sim/n=não) ");
        char opcaoCnpj = input.nextLine().charAt(0);
        
        long cnpj = fornecedorParaAlterar.getCnpj();
        if (opcaoCnpj == 's') {
            System.out.print(" - Novo CNPJ: ");
            cnpj = input.nextLong();
            input.nextLine();
        }
        
        // exibir inscrição estadual
        System.out.println("\n - Inscrição Estadual: " + 
                fornecedorParaAlterar.getInscricaoEstadual());
        // perguntar se quer alterar a inscrição estadual
        System.out.print(" --> Alterar a Inscrição Estadual? (s=sim/n=não) ");
        char opcaoInscricaoEstadual = input.nextLine().charAt(0);
        
        long inscricaoEstadual = fornecedorParaAlterar.getInscricaoEstadual();
        if (opcaoInscricaoEstadual == 's') {
            System.out.print(" - Nova Inscrição Estadual: ");
            inscricaoEstadual = input.nextLong();
            input.nextLine();
        }
        
        // exibir telefone
        System.out.println("\n - Telefone: " + 
                fornecedorParaAlterar.getTelefone());
        // perguntar se quer alterar o telefone
        System.out.print(" --> Alterar o telefone? (s=sim/n=não) ");
        char opcaoTelefone = input.nextLine().charAt(0);
        
        String telefone = fornecedorParaAlterar.getTelefone();
        if (opcaoTelefone == 's') {
            System.out.print(" - Novo telefone: ");
            telefone = input.nextLine();
        }
        
        // exibir email
        System.out.println("\n - Email: " + 
                fornecedorParaAlterar.getEmail());
        // perguntar se quer alterar o email
        System.out.print(" --> Alterar o email? (s=sim/n=não) ");
        char opcaoEmail = input.nextLine().charAt(0);
        
        String email = fornecedorParaAlterar.getEmail();
        if (opcaoEmail == 's') {
            System.out.print(" - Novo email: ");
            email = input.nextLine();
        }
        
        // confirmação final!!!
        System.out.println("\nConfirma alteração do fornecedor?");
        System.out.printf(" - Código............: %d\n", 
                fornecedorParaAlterar.getCodigo());
        System.out.printf(" - Nome fantasia.....: %s\n", nomeFantasia);
        System.out.printf(" - Razão social......: %s\n", razaoSocial);
        System.out.printf(" - Endereço..........: %s\n", endereco);
        System.out.printf(" - CNPJ..............: %d\n", cnpj);
        System.out.printf(" - Inscrição Estadual: %d\n", inscricaoEstadual);
        System.out.printf(" - Telefone..........: %s\n", telefone);
        System.out.printf(" - Email.............: %s\n", email);
        System.out.print(" --> (s=sim/n=não) ");
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            Fornecedor fornecedorAlterado = new Fornecedor(codigo, 
                    nomeFantasia, razaoSocial, endereco, cnpj, 
                    inscricaoEstadual, telefone, email);
            ArmazenamentoFornecedor.alterar(fornecedorAlterado);
        }
    }
    
    private void excluir() {
        System.out.println("\nExcluir registro de fornecedor.\n");
        
        // obter o código do fornecedor a excluir
        System.out.print(" - Código do fornecedor a excluir: ");
        long codigo = input.nextLong();
        input.nextLine();
        
        // buscar dados do fornecedor para confirmação de exclusão
        Fornecedor parametroBusca = new Fornecedor(codigo);
        Fornecedor fornecedorExcluir = ArmazenamentoFornecedor
                .buscar(parametroBusca);
        
        if (fornecedorExcluir == null) {
            System.out.println("NÃO HÁ FORNECEDOR CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }
        
        System.out.printf(" - Nome fantasia.....: %s\n", 
                fornecedorExcluir.getNomeFantasia());
        System.out.printf(" - Razão social......: %s\n", 
                fornecedorExcluir.getRazaoSocial());
        System.out.printf(" - Endereço..........: %s\n", 
                fornecedorExcluir.getEndereco());
        System.out.printf(" - CNPJ..............: %d\n", 
                fornecedorExcluir.getCnpj());
        System.out.printf(" - Inscrição Estadual: %d\n", 
                fornecedorExcluir.getInscricaoEstadual());
        System.out.printf(" - Telefone..........: %s\n", 
                fornecedorExcluir.getTelefone());
        System.out.printf(" - Email.............: %s\n", 
                fornecedorExcluir.getEmail());
        System.out.print("\n  --> Confirma exclusão? (s=sim/n=não) ");
        
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            ArmazenamentoFornecedor.excluir(fornecedorExcluir);
        }
    }
}
