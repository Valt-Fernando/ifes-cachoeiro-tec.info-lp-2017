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
                System.out.printf("| %6d | %-30s | %-15s | %18s | %15s |\n",
                        cPF.getCodigo(), cPF.getNome(), "Física", cPF.getCpf(),
                        cPF.getTelefone());
            } else if (c instanceof ClientePessoaJuridica) {
                ClientePessoaJuridica cPJ = (ClientePessoaJuridica) c;
                System.out.printf("| %6d | %-30s | %-15s | %18s | %15s |\n",
                        cPJ.getCodigo(), cPJ.getNomeFantasia(), "Jurídica", CadastroFornecedor.formatarCnpj(cPJ.getCnpj()),
                        cPJ.getTelefone());
            }
        }
        System.out.println("+--------+--------------------------------+-----------------+--------------------+-----------------+");
    }

    private void alterar() {
        System.out.println("\nAlterar registro de cliente.\n");

        // obter o código do cliente a alterar
        System.out.print(" - Código: ");
        long codigo = input.nextLong();
        input.nextLine();

        // procurar o cliente para alterar na lista de clientes
        Cliente c = new Cliente(codigo);
        Cliente clienteParaAlterar = ArmazenamentoCliente.buscar(c);

        // caso não encontre, exibir mensagem de erro ao usuário
        if (clienteParaAlterar == null) {
            System.out.println("NÃO HÁ CLIENTE CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }

        char opcaoNome, opcaoDataNascimento, opcaoSexo, opcaoCpf,
                opcaoNomeFantasia, opcaoRazaoSocial, opcaoCnpj, opcaoInscricaoEstadual,
                opcaoEndereco, opcaoTelefone, opcaoEmail;
        String nome = "", nomeFantasia = "", razaoSocial = "", endereco = "", 
                telefone = "", email = "";
        long cpf = 0, cnpj = 0, inscricaoEstadual = 0;
        char sexo = ' ';
        Date dataNascimento = Calendar.getInstance().getTime();

        // se cliente for pessoa fisica:
        if (clienteParaAlterar instanceof ClientePessoaFisica) {
            // tratar o cliente como pessoa fisica
            ClientePessoaFisica clientePF = (ClientePessoaFisica) clienteParaAlterar;

            // exibir nome
            System.out.println("\n - Nome: " + clientePF.getNome());
            // perguntar se quer alterar o nome
            System.out.print(" --> Alterar o nome? (s=sim/n=não) ");
            opcaoNome = input.nextLine().charAt(0);

            nome = clientePF.getNome();
            if (opcaoNome == 's') {
                System.out.print(" - Novo nome: ");
                nome = input.nextLine();
            }

            // exibir data de nascimento
            DateFormat df = DateFormat.getDateInstance();
            String dataFormatada = df.format(clientePF.getDataNascimento());
            System.out.println("\n - Data de nascimento: " + dataFormatada);
            // perguntar se quer alterar a data de nascimento
            System.out.print(" --> Alterar a data de nascimento? (s=sim/n=não) ");
            opcaoDataNascimento = input.nextLine().charAt(0);

            dataNascimento = clientePF.getDataNascimento();
            if (opcaoDataNascimento == 's') {
                boolean dataValida;
                do {
                    System.out.print(" - Nova data de nascimento (dd/mm/aaaa): ");
                    String data = input.nextLine();
                    try {
                        dataNascimento = DateFormat.getDateInstance().parse(data);
                        dataValida = true;
                    } catch (ParseException e) {
                        System.out.println("VOCÊ DIGITOU UMA DATA INVÁLIDA!");
                        dataValida = false;
                    }
                } while (!dataValida);
            }

            // exibir sexo
            System.out.println("\n - Sexo: " + clientePF.getSexo());
            // perguntar se quer alterar o sexo
            System.out.print(" --> Alterar o sexo? (s=sim/n=não) ");
            opcaoSexo = input.nextLine().charAt(0);

            sexo = clientePF.getSexo();
            if (opcaoSexo == 's') {
                System.out.print(" - Novo sexo: ");
                sexo = input.nextLine().charAt(0);
            }

            // exibir cpf
            System.out.println("\n - CPF: " + clientePF.getCpf());
            // perguntar se quer alterar o CPF
            System.out.print(" --> Alterar o CPF? (s=sim/n=não) ");
            opcaoCpf = input.nextLine().charAt(0);

            cpf = clientePF.getCpf();
            if (opcaoCpf == 's') {
                System.out.print(" - Novo CPF: ");
                cpf = input.nextLong();
                input.nextLine();
            }

        } else if (clienteParaAlterar instanceof ClientePessoaJuridica) {
            // tratar o cliente como pessoa juridica
            ClientePessoaJuridica clientePJ = (ClientePessoaJuridica) clienteParaAlterar;

            // exibir nome fantasia
            System.out.println("\n - Nome fantasia: " + clientePJ.getNomeFantasia());
            // perguntar se quer alterar o nome fantasia
            System.out.print(" --> Alterar o nome fantasia? (s=sim/n=não) ");
            opcaoNomeFantasia = input.nextLine().charAt(0);

            nomeFantasia = clientePJ.getNomeFantasia();
            if (opcaoNomeFantasia == 's') {
                System.out.print(" - Novo nome fantasia: ");
                nomeFantasia = input.nextLine();
            }

            // exibir razao social
            System.out.println("\n - Razao social: " + clientePJ.getRazaoSocial());
            // perguntar se quer alterar a razao social
            System.out.print(" --> Alterar a razao social? (s=sim/n=não) ");
            opcaoRazaoSocial = input.nextLine().charAt(0);

            razaoSocial = clientePJ.getRazaoSocial();
            if (opcaoRazaoSocial == 's') {
                System.out.print(" - Nova razao social: ");
                razaoSocial = input.nextLine();
            }

            // exibir cnpj
            System.out.println("\n - CNPJ: " + clientePJ.getCnpj());
            // perguntar se quer alterar o CNPJ
            System.out.print(" --> Alterar o CNPJ? (s=sim/n=não) ");
            opcaoCnpj = input.nextLine().charAt(0);

            cnpj = clientePJ.getCnpj();
            if (opcaoCnpj == 's') {
                System.out.print(" - Novo CNPJ: ");
                cnpj = input.nextLong();
                input.nextLine();
            }

            // exibir inscriçao estadual
            System.out.println("\n - Inscriçao estadual: " + clientePJ.getInscricaoEstadual());
            // perguntar se quer alterar a inscricao estadual
            System.out.print(" --> Alterar a Inscriçao Estadual? (s=sim/n=não) ");
            opcaoInscricaoEstadual = input.nextLine().charAt(0);

            inscricaoEstadual = clientePJ.getInscricaoEstadual();
            if (opcaoInscricaoEstadual == 's') {
                System.out.print(" - Nova Inscriçao Estadual: ");
                inscricaoEstadual = input.nextLong();
                input.nextLine();
            }

        }

        // exibir endereço
        System.out.println("\n - Endereço: " + clienteParaAlterar.getEndereco());
        // perguntar se quer alterar o endereço
        System.out.print(" --> Alterar o endereço? (s=sim/n=não) ");
        opcaoEndereco = input.nextLine().charAt(0);

        endereco = clienteParaAlterar.getEndereco();
        if (opcaoEndereco == 's') {
            System.out.print(" - Novo endereço: ");
            endereco = input.nextLine();
        }

        // exibir telefone
        System.out.println("\n - Telefone: " + clienteParaAlterar.getTelefone());
        // perguntar se quer alterar o telefone
        System.out.print(" --> Alterar o telefone? (s=sim/n=não) ");
        opcaoTelefone = input.nextLine().charAt(0);

        telefone = clienteParaAlterar.getTelefone();
        if (opcaoTelefone == 's') {
            System.out.print(" - Novo telefone: ");
            telefone = input.nextLine();
        }

        // exibir email
        System.out.println("\n - Email: " + clienteParaAlterar.getEmail());
        // perguntar se quer alterar o email
        System.out.print(" --> Alterar o email? (s=sim/n=não) ");
        opcaoEmail = input.nextLine().charAt(0);

        email = clienteParaAlterar.getEmail();
        if (opcaoEmail == 's') {
            System.out.print(" - Novo email: ");
            email = input.nextLine();
        }

        // confirmação final!!!
        System.out.println("\nConfirma alteração do cliente?");
        System.out.printf(" - Código: %d\n", clienteParaAlterar.getCodigo());

        // exibir dados especificos de pessoa fisica:
        if (clienteParaAlterar instanceof ClientePessoaFisica) {
            System.out.printf(" - Nome..............: %s\n", nome);
            System.out.printf(" - Data de nascimento: %s\n", 
                    DateFormat.getDateInstance().format(dataNascimento));
            System.out.printf(" - Sexo..............: %s\n", sexo);
            System.out.printf(" - CPF...............: %d\n", cpf);
        }
        // exibir dados especificos de pessoa juridica:
        else if (clienteParaAlterar instanceof ClientePessoaJuridica) {
            System.out.printf(" - Nome fantasia.....: %s\n", nomeFantasia);
            System.out.printf(" - Razao social......: %s\n", razaoSocial);
            System.out.printf(" - CNPJ..............: %s\n", 
                    CadastroFornecedor.formatarCnpj(cnpj));
            System.out.printf(" - Inscriçao estadual: %d\n", inscricaoEstadual);
        }

        // exibir dados gerais de cliente:
        System.out.printf(" - Endereço..........: %s\n", endereco);
        System.out.printf(" - Telefone..........: %s\n", telefone);
        System.out.printf(" - Email.............: %s\n", email);

        System.out.print(" --> (s=sim/n=não) ");
        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            Cliente clienteAlterado = null;
            if (clienteParaAlterar instanceof ClientePessoaFisica)
                clienteAlterado = new ClientePessoaFisica(codigo, endereco, 
                        telefone, email, cpf, sexo, nome, dataNascimento);
            else
                clienteAlterado = new ClientePessoaJuridica(codigo, endereco, 
                        telefone, email, cnpj, inscricaoEstadual, nomeFantasia, 
                        razaoSocial);

            ArmazenamentoCliente.alterar(clienteAlterado);
        }
    }

    private void excluir() {
        System.out.println("\nExcluir registro de cliente.\n");

        // obter o código do cliente a excluir
        System.out.print(" - Código do cliente a excluir: ");
        long codigo = input.nextLong();
        input.nextLine();

        // buscar dados do cliente para confirmação de exclusão
        Cliente parametroBusca = new Cliente(codigo);
        Cliente clienteExcluir = ArmazenamentoCliente.buscar(parametroBusca);

        if (clienteExcluir == null) {
            System.out.println("NÃO HÁ CLIENTE CADASTRADO COM O CÓDIGO INFORMADO.");
            return;
        }

        if (clienteExcluir instanceof ClientePessoaFisica) {
            ClientePessoaFisica c = (ClientePessoaFisica) clienteExcluir;
            System.out.printf(" - Nome..............: %s\n", c.getNome());
            System.out.printf(" - Data de nascimento: %s\n", DateFormat.getDateInstance().format(c.getDataNascimento()));
            System.out.printf(" - Sexo..............: %s\n", c.getSexo());
            System.out.printf(" - CPF...............: %s\n", c.getCpf());
        } else if (clienteExcluir instanceof ClientePessoaJuridica) {
            ClientePessoaJuridica c = (ClientePessoaJuridica) clienteExcluir;
            System.out.printf(" - Nome fantasia.....: %s\n", c.getNomeFantasia());
            System.out.printf(" - Razão social......: %s\n", c.getRazaoSocial());
            System.out.printf(" - CNPJ..............: %s\n", CadastroFornecedor.formatarCnpj(c.getCnpj()));
            System.out.printf(" - Inscrição estadual: %s\n", c.getInscricaoEstadual());
        }
        System.out.println(" - Endereço..........: " + clienteExcluir.getEndereco());
        System.out.println(" - Telefone..........: " + clienteExcluir.getTelefone());
        System.out.println(" - Email.............: " + clienteExcluir.getEmail());
        System.out.print("\n  --> Confirma exclusão? (s=sim/n=não) ");

        char opcao = input.nextLine().charAt(0);
        if (opcao == 's') {
            ArmazenamentoCliente.excluir(clienteExcluir);
        }
    }
}
