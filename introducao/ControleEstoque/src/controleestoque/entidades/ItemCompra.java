/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.entidades;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ItemCompra implements Entidade {
    private long codigo;
    private Compra compra;
    private Produto produto;
    private double precoCompra;
    private int quantidade;

    public ItemCompra(long codigo, Compra compra, Produto produto, 
            double precoCompra, int quantidade) {
        this.codigo = codigo;
        this.compra = compra;
        this.produto = produto;
        this.precoCompra = precoCompra;
        this.quantidade = quantidade;
    }
    
    public ItemCompra(long codigo) {
        this.codigo = codigo;
    }

    @Override
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
}
