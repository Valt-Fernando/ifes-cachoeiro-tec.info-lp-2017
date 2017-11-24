/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.entidades;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ItemVenda {

    private long codigo;
    private Venda venda;
    private Produto produto;
    private double precoVenda;
    private int quantidade;

    public ItemVenda(long codigo, Venda venda, Produto produto, 
            double precoCompra, int quantidade) {
        this.codigo = codigo;
        this.venda = venda;
        this.produto = produto;
        this.precoVenda = precoCompra;
        this.quantidade = quantidade;
    }
    
    public ItemVenda(long codigo) {
        this.codigo = codigo;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}
