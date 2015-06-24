/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anderson
 */
public final class ConectaMySQL {
    final private String db_driver = "com.mysql.jdbc.Driver";
    private String db_url = "jdbc:mysql://localhost/PW2_TrabalhoFinalDB";
    private String db_username = "anderson";
    private String db_password = "senha";
    private Connection db_connection;
    
    public ConectaMySQL() {
        startConnection();
    }
    
    public ConectaMySQL(String url, String username, String password) {
        db_url = url;
        db_username = username;
        db_password = password;
        startConnection();
    }
    
    public void startConnection() {
        try {
            Class.forName(this.db_driver);
            db_connection = DriverManager.getConnection(db_url, db_username, db_password);
        } catch (ClassNotFoundException | SQLException e) {
            db_connection = null;
            System.out.println(e.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void closeConnection() {
        try {
            db_connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.getLogger(ConectaMySQL.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            db_connection = null;
        }
    }

    public Connection getDb_connection() {
        return db_connection;
    }
}
