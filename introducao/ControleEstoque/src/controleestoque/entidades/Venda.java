/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.entidades;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class Venda {
    private long codigo;
    private Date data;
    private double valorTotal;
    private Vendedor vendedor;
    private Cliente cliente;
    private ArrayList<ItemVenda> itensVenda;

    public Venda(long codigo) {
        this.codigo = codigo;
    }

    public Venda(long codigo, Date data, Vendedor vendedor, Cliente cliente) {
        this.codigo = codigo;
        this.data = data;
        this.vendedor = vendedor;
        this.cliente = cliente;
        itensVenda = new ArrayList<>();
    }

    public void atualizarValorTotal() {
        double valor = 0;
        for (ItemVenda item : itensVenda) {
            valor = valor + item.getPrecoVenda() * item.getQuantidade();
        }
        valorTotal = valor;
    }
    
    public void inserirItemVenda(ItemVenda item) {
        itensVenda.add(item);
        atualizarValorTotal();
    }
    
    public boolean removerItemVenda(ItemVenda item) {
        ItemVenda itemRemover = null;
        for (ItemVenda itemLista : itensVenda) {
            if (itemLista.getCodigo() == item.getCodigo()) {
                itemRemover = itemLista;
            }
        }
        if (itemRemover != null) {
            itensVenda.remove(itemRemover);
            atualizarValorTotal();
            return true;
        }
        return false;
    }

    public long getCodigo() {
        return codigo;
    }

    public Date getData() {
        return data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
