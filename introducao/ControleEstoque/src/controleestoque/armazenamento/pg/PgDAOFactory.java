/*
 * Se for usar este código, cite o autor.
 */
package controleestoque.armazenamento.pg;

import controleestoque.armazenamento.DAOFactory;
import java.sql.Connection;

/**
 *
 * @author Alexandre Romanelli <alexandre.romanelli@ifes.edu.br>
 */
public class PgDAOFactory extends DAOFactory {
    private Connection con;
    
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/estoque";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "romanelli";
    
    
}
