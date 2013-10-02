package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	
	/**
     * get database connection for main DB
     *
     * @return connection
     * @throws SQLException
     */
    
	@SuppressWarnings("finally")
	public Connection getDBConnection() throws SQLException {

        String dbDriver = "com.mysql.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/parser";
        String dbUsername = "root";
        String dbPassword = "black";
        Connection conn = null;
       
        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            DriverManager.setLoginTimeout(60);
            if (conn != null) {
                return conn;
            }
        } finally {
            return conn;
        }
    }

}
