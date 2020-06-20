package pl.coderslab.workshop2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static String DB_URL = "jdbc:mysql://localhost:3306/--dbname--?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static String DB_USER = "root";
    private static String DB_PASS = "coderslab";


    public static Connection getConnection(String dbName) throws SQLException {
        String conDbName = DB_URL.replace("--dbname--",dbName);

        return DriverManager.getConnection(conDbName,DB_USER,DB_PASS);
    }


}
