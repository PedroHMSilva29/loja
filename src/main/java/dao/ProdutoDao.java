package dao;

import entities.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProdutoDao {

    private List<Produto> produtos = new ArrayList<>();
    private Connection connection;

    public ProdutoDao(Connection connection){
        this.connection = connection;
    }

    public void adiciona(Produto produto){
        produtos.add(produto);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public List<Produto> listar() throws SQLException {
        
        Statement statement = connection.createStatement();
        statement.execute("SELECT id, nome, descricao FROM PRODUTO");
        ResultSet result = statement.getResultSet();

        while(result.next()){
            Integer id = result.getInt("id");
            String nome = result.getString("nome");
            String descricao = result.getString("descricao");

            adiciona(new Produto(id, nome, descricao));
        }

        return getProdutos();

    }

    public void salvar(Produto produto) throws SQLException {

        connection.setAutoCommit(false);

        try(PreparedStatement st = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)){

            st.setString(1, produto.getNome());
            st.setString(2, produto.getDescricao());

            st.execute();

            try(ResultSet result = st.getGeneratedKeys()){
                while(result.next()){
                    produto.setId(result.getInt(1));
                    System.out.println("Produto inserido, " + produto);
                }
            }
            connection.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("rollback realizado");
            connection.rollback();
        }

    }

    public void delete() throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM PRODUTO WHERE id = 2");

        System.out.println("Registros removidos: "+ statement.getUpdateCount());
    }

    public void inserirInicial(String nome, String descricao) throws SQLException {

        Statement statement = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO PRODUTO (nome, descricao) VALUES ('");
        sb.append(nome);
        sb.append("','");
        sb.append(descricao);
        sb.append("')");

        statement.execute(sb.toString(), Statement.RETURN_GENERATED_KEYS);

        ResultSet result = statement.getGeneratedKeys();
        while(result.next()){
            System.out.println(result.getInt(1));
        }

    }


}
