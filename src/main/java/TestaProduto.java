import dao.ProdutoDao;
import factories.ConnectionFactory;
import entities.Produto;

import java.sql.*;

public class TestaProduto {
    public static void main(String[] args) throws SQLException {

        Produto produto = new Produto("Playstation 5", "Sony");

        ConnectionFactory factory = new ConnectionFactory();
        try(Connection con = factory.getConnection()){
            ProdutoDao dao = new ProdutoDao(con);

            //dao.salvar(produto);
            dao.listar().forEach(l -> System.out.println(l));

        }

    }

}
