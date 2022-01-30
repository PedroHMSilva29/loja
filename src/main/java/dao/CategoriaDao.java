package dao;

import entities.Categoria;
import entities.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoriaDao {

    private List<Categoria> categorias = new ArrayList<>();
    private Connection connection;

    private void adiciona(Categoria categoria){
        categorias.add(categoria);}

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public CategoriaDao(Connection connection){
        this.connection = connection;
    }

    public List<Categoria>  listar() throws SQLException {

        try(PreparedStatement ps =  connection.prepareStatement("SELECT * FROM categoria")){
            ps.execute();
            try(ResultSet result = ps.getResultSet()){
                while(result.next()){
                    this.adiciona(new Categoria(result.getInt(1), result.getString(2)));
                }

            }

        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Ocorreu um erro inesperado, rollback realizado");
        }
        return getCategorias();
    }

    public List<Categoria> listarProdutosPorCategoria(){

        try(PreparedStatement ps =  connection.prepareStatement("SELECT p.id, p.nome, p.descricao, c.id, c.nome FROM categoria c INNER JOIN produto p ON c.id = p.id_categoria")){
            ps.execute();
            try(ResultSet result = ps.getResultSet()){
                while(result.next()){

                    Produto produto = new Produto(result.getInt(1), result.getString(2), result.getString(3));
                    Categoria categoria = new Categoria( result.getInt(4), result.getString(5));

                    categoria.adiciona(produto);
                    categorias.add(categoria);

                }

            }

        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Ocorreu um erro inesperado, rollback realizado");
        }
        return getCategorias();
    }

    public void salvar(Categoria categoria) throws SQLException {

        connection.setAutoCommit(false);

        try(PreparedStatement ps =  connection.prepareStatement("INSERT INTO categoria (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, categoria.getNome());

            ps.execute();

            try(ResultSet result = ps.getGeneratedKeys()){
                while(result.next()){
                    categoria.setId(result.getInt(1));
                    this.adiciona(categoria);
                }
                connection.commit();
                System.out.println("Categoria salva com sucesso: "+ categoria);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            connection.rollback();
            System.out.println("Ocorreu um erro inesperado, rollback realizado");
        }

    }
}
