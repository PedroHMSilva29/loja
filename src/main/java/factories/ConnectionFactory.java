package factories;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    private String url;
    private String user;
    private String password;
    private DataSource dataSource;

    public ConnectionFactory(){
        this.url = "jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC";
        this.user = "root";
        this.password = "root";

        ComboPooledDataSource comboPooled = new ComboPooledDataSource();
        comboPooled.setJdbcUrl(this.url);
        comboPooled.setUser(this.user);
        comboPooled.setPassword(this.password);

        comboPooled.setMaxPoolSize(10);

        this.dataSource = comboPooled;
    }

    public Connection getConnection() throws SQLException {
        //return DriverManager.getConnection(this.url, this.user, this.password);
        return dataSource.getConnection();
    }

}
