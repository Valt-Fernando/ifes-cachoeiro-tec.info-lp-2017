/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento;

import controleestoque.entidades.Entidade;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public abstract class Armazenamento {
    
    private ArrayList<Entidade> lista;
    
    public ArrayList getLista() {
        return lista;
    }
    
    public void iniciarLista() {
        lista = new ArrayList<>();
    }
    
    public void inserir(Entidade e) {
        lista.add(e);
    }
    
    public abstract boolean alterar(Entidade e);
    
    public boolean excluir(Entidade e) {
        Entidade entidadeExcluir = buscar(e);
        if (entidadeExcluir != null) {
            lista.remove(entidadeExcluir);
            return true;
        }
        return false;
    }
    
    public Entidade buscar(Entidade e) {
        for (Entidade entidade : lista) {
            if (entidade.getCodigo() == e.getCodigo()) {
                return entidade;
            }
        }
        return null;
    }
    
}
