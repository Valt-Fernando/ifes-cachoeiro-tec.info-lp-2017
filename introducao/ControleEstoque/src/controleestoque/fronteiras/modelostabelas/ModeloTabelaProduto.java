/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.fronteiras.modelostabelas;

import controleestoque.armazenamento.ProdutoDAO;
import controleestoque.entidades.Produto;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class ModeloTabelaProduto extends AbstractTableModel {
    
    private ProdutoDAO produtoDAO;
    
    private static final String[] NOMES_COLUNAS = {
        "Cód.", "Nome", "Preço"
    };
    
    public ModeloTabelaProduto(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }
    
    @Override
    public int getRowCount() {
        return produtoDAO.getLista().size();
    }

    @Override
    public int getColumnCount() {
        return NOMES_COLUNAS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = produtoDAO.getLista().get(rowIndex);
        switch (columnIndex) {
            case 0: return produto.getCodigo();
            case 1: return produto.getNome();
            case 2: return produto.getPreco();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return NOMES_COLUNAS[column];
    }
    
}
