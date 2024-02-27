/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class MSSQLConnector implements IDatabaseConnector {
       public String URL;
       private String USER;
       private String PASSWORD;
       
       public MSSQLConnector(String URL, String USER, String PASSWORD)
       {
           this.URL = URL;
           this.USER = USER;
           this.PASSWORD = PASSWORD;
       }
       
    @Override
    public int executeUpdate(String query)  {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
        Statement statement = connection.createStatement();
        int affectedRows = statement.executeUpdate(query);
        if (connection != null) {
            connection.close();
        }
        return affectedRows;
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            return 0;
        }
    }
    
       @Override
    public ResultSet executeQuery(String query) {
        
           try {
               Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
               Statement statement = connection.createStatement();
               
               return statement.executeQuery(query);
               
               
           } catch (SQLException ex) {
               System.err.println("Error connecting to the databaseq: " + ex.getMessage());
               return null;
           }
        
    }
    
    
}
