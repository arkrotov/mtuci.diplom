package analyzer.database;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class Database {

    private final String URL = "jdbc:mysql://localhost:3306/analyzer";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    private Driver driver = null;
    public Connection connection = null;

    public Database() {
        try {
            driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить драйвер");
        }
    }
}

