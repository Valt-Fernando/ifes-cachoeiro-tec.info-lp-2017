/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.entidades;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ClientePessoaJuridica extends Cliente {
    private long cnpj;
    private long inscricaoEstadual;
    private String nomeFantasia;
    private String razaoSocial;
    
    public ClientePessoaJuridica(long codigo, String endereco, String telefone, 
            String email, long cnpj, long inscricaoEstadual, String nomeFantasia,
            String razaoSocial) {
        super(codigo, endereco, telefone, email);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
    }

    public long getCnpj() {
        return cnpj;
    }

    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
    }

    public long getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(long inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    
}
