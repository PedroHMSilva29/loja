import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import dao.CategoriaDao;
import entities.Categoria;
import factories.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaCategoria {

    public static void main(String[] args) throws SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        Categoria livro = new Categoria("LIVROS");
        try(Connection con = factory.getConnection()){
            CategoriaDao dao = new CategoriaDao(con);
            //dao.salvar(livro);
            //dao.listar().forEach(c -> System.out.println(c));
            dao.listarProdutosPorCategoria().forEach(c -> System.out.println(c));
        }
    }
}
