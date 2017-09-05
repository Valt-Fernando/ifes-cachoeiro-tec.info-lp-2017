/*
 * Se for usar este código, cite o autor.
 */
package controleestoque;

import controleestoque.armazenamento.ArmazenamentoCliente;
import controleestoque.armazenamento.ArmazenamentoFornecedor;
import controleestoque.armazenamento.ArmazenamentoFuncionario;
import controleestoque.armazenamento.ArmazenamentoProduto;
import controleestoque.entidades.Cliente;
import controleestoque.entidades.ClientePessoaFisica;
import controleestoque.entidades.ClientePessoaJuridica;
import controleestoque.entidades.Comprador;
import controleestoque.entidades.Fornecedor;
import controleestoque.entidades.Funcionario;
import controleestoque.entidades.Produto;
import controleestoque.entidades.Vendedor;
import controleestoque.fronteiras.MenuPrincipal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ControleEstoque {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // inicialização dos dados:
        ArmazenamentoProduto.iniciarLista();
        ArmazenamentoFornecedor.iniciarLista();
        ArmazenamentoCliente.iniciarLista();
        ArmazenamentoFuncionario.iniciarLista();
        
        cadastrarDadosParaTestes();
        
        MenuPrincipal qualquerNome = new MenuPrincipal();
        qualquerNome.exibirMenu();
    }
    
    private static void cadastrarDadosParaTestes() {
        ArrayList<Produto> listaProduto = ArmazenamentoProduto.getLista();
        ArrayList<Fornecedor> listaFornecedor = ArmazenamentoFornecedor.getLista();
        ArrayList<Cliente> listaCliente = ArmazenamentoCliente.getLista();
        ArrayList<Funcionario> listaFuncionario = ArmazenamentoFuncionario.getLista();
        
        listaProduto.add(new Produto(1, "Banana", 2.5));
        listaProduto.add(new Produto(2, "Melão", 5.4));
        listaProduto.add(new Produto(3, "Maçã", 3.5));
        listaProduto.add(new Produto(4, "Abacaxi", 3.0));
        listaProduto.add(new Produto(5, "Melancia", 6.0));
        
        listaFornecedor.add(new Fornecedor(1, "Bananas EP", "Bananas EP Ltda", 
                "Rua 123, n.34 - Bairro do Limoeiro - Indaiatuba - SP", 
                12938910001L, 12938190, "(28)3245-0981", 
                "bananas@bananas.com.br"));
        listaFornecedor.add(new Fornecedor(2, "Horti-Verde", "Comercial Horti-Verde Ltda-Me", 
                "Rua 124, n.23 - Bairro do Limoeiro - Indaiatuba - SP", 
                129389101321L, 12938123, "(28)3245-0000", 
                "hortiverde@hortiverde.com.br"));
        listaFornecedor.add(new Fornecedor(3, "Resplendor", "Comercial Resplendor Ltda-Me", 
                "Rua 145, n.65 - Bairro do Limoeiro - Indaiatuba - SP", 
                124674256895L, 12918390, "(28)3442-0011", 
                "resplendor@resplendor.com.br"));

        try {
            listaCliente.add(new ClientePessoaFisica(1, 
                    "Rua Alberto Santos Dumont, 198 - Bairro das Arraias - Marataízes - ES", 
                    "(28)99987-0911", 
                    "matusalem@matusa.com.br", 
                    10625478954L, 
                    'M', 
                    "Matusalem da Silva", 
                    DateFormat.getDateInstance().parse("02/05/1931")));
            listaCliente.add(new ClientePessoaJuridica(2, 
                    "Rua Alberto Santos Dumont, 198 - Bairro das Arraias - Marataízes - ES", 
                    "(28)3245-0012", 
                    "fruvit@fruvit.com.br", 
                    145214587552L, 
                    125478598, 
                    "Fruvit", 
                    "Fruvit Frutas Vitoria Ltda"));
            listaCliente.add(new ClientePessoaFisica(3, 
                    "Rua Amphilóphio Boaventura Sant'Anna, 11 - Bairro dos Cações - Marataízes - ES", 
                    "(28)99920-0123", 
                    "uatissom@orkut.com.br", 
                    14582657485L, 
                    'M', 
                    "Uatissom dos Santos", 
                    DateFormat.getDateInstance().parse("30/07/1997")));
        } catch (ParseException e) {
            // nada a fazer...
        }
        
        // funcionários
        listaFuncionario.add(new Vendedor(1, "Whoopi Harris", 7625544905L,"P.O. Box 789, 1156 Nullam Road","83-39092-5888","Sed.eu@gravidanuncsed.org"));
        listaFuncionario.add(new Comprador(2, "Mercedes Conner", 4914862412L, "823-7258 Quam Rd.","06-26330-4494","Morbi.accumsan@dolorFusce.edu"));
        listaFuncionario.add(new Vendedor(3, "Leo Leonard", 1726795296L, "351-393 Et Rd.", "43-89453-2921", "auctor.ullamcorper.nisl@loremsemper.co.uk"));
        listaFuncionario.add(new Comprador(4, "Desiree Vincent", 1956097847L, "P.O. Box 316, 137 Luctus Road", "90-97352-4242", "Sed.eget.lacus@Integertincidunt.net"));
        listaFuncionario.add(new Vendedor(5, "Jessica Berger", 2936004070L, "Ap #950-2160 Et Street", "21-18198-1646", "Cal@Aeneanegestas.co.uk"));
    }
    
}
