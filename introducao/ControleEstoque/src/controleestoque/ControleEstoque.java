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
import controleestoque.entidades.Compra;
import controleestoque.entidades.Comprador;
import controleestoque.entidades.Fornecedor;
import controleestoque.entidades.Funcionario;
import controleestoque.entidades.ItemCompra;
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
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws ParseException {
        // inicialização dos dados:
        ArmazenamentoProduto.iniciarLista();
        ArmazenamentoFornecedor.iniciarLista();
        ArmazenamentoCliente.iniciarLista();
        ArmazenamentoFuncionario.iniciarLista();
//        ArmazenamentoCompra.iniciarLista();
        
        cadastrarDadosParaTestes();
        
        MenuPrincipal qualquerNome = new MenuPrincipal();
        qualquerNome.exibirMenu();
    }
    
    private static void cadastrarDadosParaTestes() throws ParseException {
        ArrayList<Produto> listaProduto = ArmazenamentoProduto.getLista();
        ArrayList<Fornecedor> listaFornecedor = ArmazenamentoFornecedor.getLista();
        ArrayList<Cliente> listaCliente = ArmazenamentoCliente.getLista();
        ArrayList<Funcionario> listaFuncionario = ArmazenamentoFuncionario.getLista();
//        ArrayList<Compra> listaCompra = ArmazenamentoCompra.getLista();
        
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
        Comprador comprador1 = new Comprador(2, "Mercedes Conner", 4914862412L, "823-7258 Quam Rd.","06-26330-4494","Morbi.accumsan@dolorFusce.edu");
        Comprador comprador2 = new Comprador(4, "Desiree Vincent", 1956097847L, "P.O. Box 316, 137 Luctus Road", "90-97352-4242", "Sed.eget.lacus@Integertincidunt.net");
        listaFuncionario.add(new Vendedor(1, "Whoopi Harris", 7625544905L,"P.O. Box 789, 1156 Nullam Road","83-39092-5888","Sed.eu@gravidanuncsed.org"));
        listaFuncionario.add(comprador1);
        listaFuncionario.add(new Vendedor(3, "Leo Leonard", 1726795296L, "351-393 Et Rd.", "43-89453-2921", "auctor.ullamcorper.nisl@loremsemper.co.uk"));
        listaFuncionario.add(comprador2);
        listaFuncionario.add(new Vendedor(5, "Jessica Berger", 2936004070L, "Ap #950-2160 Et Street", "21-18198-1646", "Cal@Aeneanegestas.co.uk"));
        
        // compras
        Compra c1 = new Compra(1, DateFormat.getDateInstance().parse("20/09/2017"), comprador2, listaFornecedor.get(0));
        c1.inserirItemCompra(new ItemCompra(0, c1, listaProduto.get(0), 2.4, 30));
        c1.inserirItemCompra(new ItemCompra(1, c1, listaProduto.get(1), 5.2, 20));
        c1.inserirItemCompra(new ItemCompra(2, c1, listaProduto.get(3), 2.7, 15));
        Compra c2 = new Compra(2, DateFormat.getDateInstance().parse("20/09/2017"), comprador1, listaFornecedor.get(1));
        c2.inserirItemCompra(new ItemCompra(0, c2, listaProduto.get(2), 3.4, 25));
        c2.inserirItemCompra(new ItemCompra(1, c2, listaProduto.get(3), 2.75, 10));
        Compra c3 = new Compra(3, DateFormat.getDateInstance().parse("22/09/2017"), comprador1, listaFornecedor.get(2));
        c3.inserirItemCompra(new ItemCompra(0, c3, listaProduto.get(0), 2.3, 40));
        Compra c4 = new Compra(4, DateFormat.getDateInstance().parse("23/09/2017"), comprador2, listaFornecedor.get(1));
        c4.inserirItemCompra(new ItemCompra(0, c4, listaProduto.get(2), 3.3, 30));
        c4.inserirItemCompra(new ItemCompra(1, c4, listaProduto.get(3), 2.7, 20));
        Compra c5 = new Compra(5, DateFormat.getDateInstance().parse("03/10/2017"), comprador1, listaFornecedor.get(0));
        c5.inserirItemCompra(new ItemCompra(0, c5, listaProduto.get(1), 5.15, 25));
//        listaCompra.add(c1);
//        listaCompra.add(c2);
//        listaCompra.add(c3);
//        listaCompra.add(c4);
//        listaCompra.add(c5);
    }
    
}
