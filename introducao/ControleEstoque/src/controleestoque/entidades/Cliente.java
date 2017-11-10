/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.entidades;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class Cliente implements Entidade {
    private long codigo;
    private String endereco;
    private String telefone;
    private String email;
    
    public Cliente(long codigo) {
        this.codigo = codigo;
    }

    public Cliente(long codigo, String endereco, String telefone, String email) {
        this.codigo = codigo;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    @Override
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
