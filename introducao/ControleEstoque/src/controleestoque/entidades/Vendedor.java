/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.entidades;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class Vendedor extends Funcionario {

    public Vendedor(long codigo, String nome, long cpf, String endereco, 
            String telefone, String email) {
        super(codigo, nome, cpf, endereco, telefone, email);
    }
    
    public Vendedor(String nome, long cpf, String endereco, String telefone,
            String email) {
        super(nome, cpf, endereco, telefone, email);
    }
   
}
