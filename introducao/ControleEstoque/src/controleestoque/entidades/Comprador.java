/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.entidades;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class Comprador extends Funcionario {
   
    public Comprador(long codigo, String nome, long cpf, String endereco, 
            String telefone, String email) {
        super(codigo, nome, cpf, endereco, telefone, email);
    }
   
}
